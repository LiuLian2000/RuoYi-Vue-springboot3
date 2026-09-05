package com.ruoyi.errorcode.service.impl;

import java.util.List;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.errorcode.mapper.ErrorCodeMapper;
import com.ruoyi.errorcode.mapper.ErrorSeverityMapper;
import com.ruoyi.errorcode.domain.ErrorSeverity;
import com.ruoyi.errorcode.service.IErrorSeverityService;

/**
 * 错误码严重程度Service业务层处理
 * 
 * @author ruoyi
 * @date 2026-08-26
 */
@Service
public class ErrorSeverityServiceImpl implements IErrorSeverityService 
{
    private static final Integer DISABLED = 0;

    @Autowired
    private ErrorSeverityMapper errorSeverityMapper;

    @Autowired
    private ErrorCodeMapper errorCodeMapper;

    /**
     * 查询错误码严重程度
     * 
     * @param id 错误码严重程度主键
     * @return 错误码严重程度
     */
    @Override
    public ErrorSeverity selectErrorSeverityById(Long id)
    {
        return errorSeverityMapper.selectErrorSeverityById(id);
    }

    /**
     * 查询错误码严重程度列表
     * 
     * @param errorSeverity 错误码严重程度
     * @return 错误码严重程度
     */
    @Override
    public List<ErrorSeverity> selectErrorSeverityList(ErrorSeverity errorSeverity)
    {
        return errorSeverityMapper.selectErrorSeverityList(errorSeverity);
    }

    /**
     * 新增错误码严重程度
     * 
     * @param errorSeverity 错误码严重程度
     * @return 结果
     */
    @Override
    public int insertErrorSeverity(ErrorSeverity errorSeverity)
    {
        return errorSeverityMapper.insertErrorSeverity(errorSeverity);
    }

    /**
     * 修改错误码严重程度
     * 
     * @param errorSeverity 错误码严重程度
     * @return 结果
     */
    @Override
    @Transactional
    public int updateErrorSeverity(ErrorSeverity errorSeverity)
    {
        if (errorSeverity.getId() == null)
        {
            throw new ServiceException("严重程度ID不能为空");
        }
        if (errorSeverity.getSeverityCode() == null
                && !hasText(errorSeverity.getSeverityName())
                && errorSeverity.getDescription() == null
                && errorSeverity.getStatus() == null)
        {
            throw new ServiceException("没有需要修改的内容");
        }
        requireVersion(errorSeverity.getVersion());
        return updateWithCurrentStateCheck(errorSeverity);
    }

    /** 完整对象更新时锁定当前记录，并在实际停用时检查错误码引用。 */
    private int updateWithCurrentStateCheck(ErrorSeverity errorSeverity)
    {
        List<ErrorSeverity> lockedSeverities = errorSeverityMapper.selectErrorSeveritiesByIdsForUpdate(
                new Long[] { errorSeverity.getId() });
        if (lockedSeverities.isEmpty())
        {
            throw new ServiceException("严重程度不存在或已删除");
        }
        ErrorSeverity existing = lockedSeverities.get(0);
        requireCurrentVersion(errorSeverity.getVersion(), existing.getVersion());
        boolean disabling = DISABLED.equals(errorSeverity.getStatus())
                && !DISABLED.equals(existing.getStatus());
        if (disabling && errorCodeMapper.countEnabledErrorCodesBySeverityIds(
                new Long[] { existing.getId() }) > 0)
        {
            throw new ServiceException("严重程度已被启用的错误码引用，请先停用相关错误码");
        }
        return executeUpdate(errorSeverity);
    }

    private int executeUpdate(ErrorSeverity errorSeverity)
    {
        int rows = errorSeverityMapper.updateErrorSeverity(errorSeverity);
        if (rows == 0)
        {
            throw new ServiceException("数据不存在、已删除或已被其他用户修改，请刷新后重试");
        }
        return rows;
    }

    @Override
    @Transactional
    public int updateErrorSeverityBatch(List<ErrorSeverity> errorSeverities)
    {
        int rows = 0;
        for (ErrorSeverity errorSeverity : BatchUpdateSupport.prepare(errorSeverities, ErrorSeverity::getId))
        {
            rows += updateErrorSeverity(errorSeverity);
        }
        return rows;
    }

    /**
     * 批量删除错误码严重程度
     * 
     * @param ids 需要删除的错误码严重程度主键
     * @return 结果
     */
    @Override
    @Transactional
    public int deleteErrorSeverityByIds(Long[] ids)
    {
        if (ids == null || ids.length == 0)
        {
            return 0;
        }

        List<ErrorSeverity> lockedSeverities = errorSeverityMapper.selectErrorSeveritiesByIdsForUpdate(ids);
        List<Long> severityIds = new ArrayList<>();
        for (ErrorSeverity severity : lockedSeverities)
        {
            severityIds.add(severity.getId());
        }
        if (!severityIds.isEmpty()
                && errorCodeMapper.countErrorCodesBySeverityIds(
                        severityIds.toArray(new Long[0])) > 0)
        {
            throw new ServiceException("所选严重程度已被错误码引用，不能删除");
        }
        return errorSeverityMapper.deleteErrorSeverityByIds(ids);
    }

    /**
     * 删除错误码严重程度信息
     * 
     * @param id 错误码严重程度主键
     * @return 结果
     */
    @Override
    @Transactional
    public int deleteErrorSeverityById(Long id)
    {
        return deleteErrorSeverityByIds(new Long[] { id });
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

    private void requireVersion(Integer version)
    {
        if (version == null)
        {
            throw new ServiceException("当前页面数据已失效，请刷新后重试");
        }
    }
}
