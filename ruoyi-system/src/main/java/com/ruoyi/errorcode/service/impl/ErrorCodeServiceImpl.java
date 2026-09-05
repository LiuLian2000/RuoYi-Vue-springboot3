package com.ruoyi.errorcode.service.impl;

import java.util.List;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.errorcode.domain.ErrorCategory;
import com.ruoyi.errorcode.domain.ErrorSeverity;
import com.ruoyi.errorcode.domain.ErrorSystem;
import com.ruoyi.errorcode.mapper.ErrorCategoryMapper;
import com.ruoyi.errorcode.mapper.ErrorCodeMapper;
import com.ruoyi.errorcode.mapper.ErrorSeverityMapper;
import com.ruoyi.errorcode.mapper.ErrorSystemMapper;
import com.ruoyi.errorcode.domain.ErrorCode;
import com.ruoyi.errorcode.service.IErrorCodeService;

/**
 * 错误码Service业务层处理
 * 
 * @author ruoyi
 * @date 2026-08-26
 */
@Service
public class ErrorCodeServiceImpl implements IErrorCodeService 
{
    private static final Integer ENABLED = 1;

    @Autowired
    private ErrorCodeMapper errorCodeMapper;

    @Autowired
    private ErrorSystemMapper errorSystemMapper;

    @Autowired
    private ErrorCategoryMapper errorCategoryMapper;

    @Autowired
    private ErrorSeverityMapper errorSeverityMapper;

    /**
     * 查询错误码
     * 
     * @param id 错误码主键
     * @return 错误码
     */
    @Override
    public ErrorCode selectErrorCodeById(Long id)
    {
        return errorCodeMapper.selectErrorCodeById(id);
    }

    /**
     * 查询错误码列表
     * 
     * @param errorCode 错误码
     * @return 错误码
     */
    @Override
    public List<ErrorCode> selectErrorCodeList(ErrorCode errorCode)
    {
        return errorCodeMapper.selectErrorCodeList(errorCode);
    }

    /**
     * 新增错误码
     * 
     * @param errorCode 错误码
     * @return 结果
     */
    @Override
    @Transactional
    public int insertErrorCode(ErrorCode errorCode)
    {
        // 这行不能删，虽然表里有默认值1。但是有可能新增的是0，后面检查上级是否状态都是一样的时候还要用
        Integer targetStatus = errorCode.getStatus() == null ? ENABLED : errorCode.getStatus();
        validateReferences(errorCode.getSystemId(), errorCode.getCategoryId(),
                errorCode.getSeverityId(), ENABLED.equals(targetStatus));
        return errorCodeMapper.insertErrorCode(errorCode);
    }

    /**
     * 修改错误码
     * 
     * @param errorCode 错误码
     * @return 结果
     */
    @Override
    @Transactional
    public int updateErrorCode(ErrorCode errorCode)
    {
        if (errorCode.getId() == null)
        {
            throw new ServiceException("错误码ID不能为空");
        }
        ErrorCode existing = errorCodeMapper.selectErrorCodeById(errorCode.getId());
        if (existing == null)
        {
            throw new ServiceException("错误码不存在或已删除");
        }
        if (errorCode.getSystemId() == null
                && errorCode.getCategoryId() == null
                && !hasText(errorCode.getCode())
                && !hasText(errorCode.getMessage())
                && errorCode.getDescription() == null
                && errorCode.getSeverityId() == null
                && errorCode.getStatus() == null)
        {
            throw new ServiceException("没有需要修改的内容");
        }
        requireCurrentVersion(errorCode.getVersion(), existing.getVersion());

        Long systemId = errorCode.getSystemId() == null ? existing.getSystemId() : errorCode.getSystemId();
        Long categoryId = errorCode.getCategoryId() == null ? existing.getCategoryId() : errorCode.getCategoryId();
        Long severityId = errorCode.getSeverityId() == null
                ? existing.getSeverityId() : errorCode.getSeverityId();
        Integer targetStatus = errorCode.getStatus() == null ? existing.getStatus() : errorCode.getStatus();

        boolean associationChanged = !Objects.equals(systemId, existing.getSystemId())
                || !Objects.equals(categoryId, existing.getCategoryId())
                || !Objects.equals(severityId, existing.getSeverityId());
        boolean enabling = ENABLED.equals(targetStatus) && !ENABLED.equals(existing.getStatus());
        if (associationChanged || enabling)
        {
            return updateWithReferenceCheck(errorCode, systemId, categoryId, severityId,
                    ENABLED.equals(targetStatus));
        }

        return executeUpdate(errorCode);
    }

    /** 启用错误码或修改关联关系时，锁定并校验系统、类别和严重程度。 */
    private int updateWithReferenceCheck(ErrorCode errorCode, Long systemId,
            Long categoryId, Long severityId, boolean requireEnabled)
    {
        validateReferences(systemId, categoryId, severityId, requireEnabled);
        return executeUpdate(errorCode);
    }

    private int executeUpdate(ErrorCode errorCode)
    {
        int rows = errorCodeMapper.updateErrorCode(errorCode);
        if (rows == 0)
        {
            throw new ServiceException("数据不存在、已删除或已被其他用户修改，请刷新后重试");
        }
        return rows;
    }

    @Override
    @Transactional
    public int updateErrorCodeBatch(List<ErrorCode> errorCodes)
    {
        int rows = 0;
        for (ErrorCode errorCode : BatchUpdateSupport.prepare(errorCodes, ErrorCode::getId))
        {
            rows += updateErrorCode(errorCode);
        }
        return rows;
    }

    /**
     * 批量删除错误码
     * 
     * @param ids 需要删除的错误码主键
     * @return 结果
     */
    @Override
    public int deleteErrorCodeByIds(Long[] ids)
    {
        if (ids == null || ids.length == 0)
        {
            return 0;
        }
        return errorCodeMapper.deleteErrorCodeByIds(ids);
    }

    /**
     * 删除错误码信息
     * 
     * @param id 错误码主键
     * @return 结果
     */
    @Override
    public int deleteErrorCodeById(Long id)
    {
        return errorCodeMapper.deleteErrorCodeById(id);
    }

    private void validateReferences(Long systemId, Long categoryId, Long severityId,
            boolean requireEnabled)
    {
        if (systemId == null || categoryId == null || severityId == null)
        {
            throw new ServiceException("所属系统、所属类别和严重程度不能为空");
        }

        List<ErrorSystem> systems = errorSystemMapper.selectErrorSystemsByIdsForUpdate(
                new Long[] { systemId });
        if (systems.isEmpty())
        {
            throw new ServiceException("所属系统不存在或已删除");
        }
        ErrorSystem system = systems.get(0);

        List<ErrorCategory> categories = errorCategoryMapper.selectErrorCategoriesByIdsForUpdate(
                new Long[] { categoryId });
        if (categories.isEmpty())
        {
            throw new ServiceException("所属类别不存在或已删除");
        }
        ErrorCategory category = categories.get(0);
        if (!systemId.equals(category.getSystemId()))
        {
            throw new ServiceException("所属类别不属于所选系统");
        }

        List<ErrorSeverity> severities = errorSeverityMapper.selectErrorSeveritiesByIdsForUpdate(
                new Long[] { severityId });
        if (severities.isEmpty())
        {
            throw new ServiceException("严重程度不存在或已删除");
        }

        if (requireEnabled && (!ENABLED.equals(system.getStatus())
                || !ENABLED.equals(category.getStatus())
                || !ENABLED.equals(severities.get(0).getStatus())))
        {
            throw new ServiceException("启用错误码时，所属系统、类别和严重程度必须全部启用");
        }
    }

    private boolean hasText(String value)
    {
        return value != null && !value.isBlank();
    }

    private void requireCurrentVersion(Integer requestVersion, Integer currentVersion)
    {
        if (requestVersion == null)
        {
            throw new ServiceException("当前页面数据已失效，请刷新后重试");
        }
        if (!requestVersion.equals(currentVersion))
        {
            throw new ServiceException("数据已被其他用户修改，请刷新后重试");
        }
    }
}
