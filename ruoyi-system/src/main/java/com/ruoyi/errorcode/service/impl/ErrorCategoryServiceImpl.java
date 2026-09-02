package com.ruoyi.errorcode.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.errorcode.mapper.ErrorCodeMapper;
import com.ruoyi.errorcode.mapper.ErrorSystemMapper;
import com.ruoyi.errorcode.mapper.ErrorCategoryMapper;
import com.ruoyi.errorcode.domain.ErrorCategory;
import com.ruoyi.errorcode.domain.ErrorSystem;
import com.ruoyi.errorcode.service.IErrorCategoryService;

/**
 * 错误码类别Service业务层处理
 * 
 * @author ruoyi
 * @date 2026-08-26
 */
@Service
public class ErrorCategoryServiceImpl implements IErrorCategoryService 
{
    private static final Integer ENABLED = 1;
    private static final Integer DISABLED = 0;

    @Autowired
    private ErrorCategoryMapper errorCategoryMapper;

    @Autowired
    private ErrorSystemMapper errorSystemMapper;

    @Autowired
    private ErrorCodeMapper errorCodeMapper;

    /**
     * 查询错误码类别
     * 
     * @param id 错误码类别主键
     * @return 错误码类别
     */
    @Override
    public ErrorCategory selectErrorCategoryById(Long id)
    {
        return errorCategoryMapper.selectErrorCategoryById(id);
    }

    /**
     * 查询错误码类别列表
     * 
     * @param errorCategory 错误码类别
     * @return 错误码类别
     */
    @Override
    public List<ErrorCategory> selectErrorCategoryList(ErrorCategory errorCategory)
    {
        return errorCategoryMapper.selectErrorCategoryList(errorCategory);
    }

    /**
     * 新增错误码类别
     * 
     * @param errorCategory 错误码类别
     * @return 结果
     */
    @Override
    @Transactional
    public int insertErrorCategory(ErrorCategory errorCategory)
    {
        ErrorSystem system = requireLockedSystem(errorCategory.getSystemId());
        if ((errorCategory.getStatus() == null || ENABLED.equals(errorCategory.getStatus()))
                && !ENABLED.equals(system.getStatus()))
        {
            throw new ServiceException("所属系统未启用，不能新增启用类别");
        }
        return errorCategoryMapper.insertErrorCategory(errorCategory);
    }

    /**
     * 修改错误码类别
     * 
     * @param errorCategory 错误码类别
     * @return 结果
     */
    @Override
    @Transactional
    public int updateErrorCategory(ErrorCategory errorCategory)
    {
        if (errorCategory.getId() == null)
        {
            throw new ServiceException("类别ID不能为空");
        }
        if (!hasText(errorCategory.getCategoryCode())
                && !hasText(errorCategory.getCategoryName())
                && errorCategory.getDescription() == null
                && errorCategory.getStatus() == null)
        {
            if (errorCategory.getSystemId() != null)
            {
                validateSystemUnchanged(errorCategory, requireCategory(errorCategory.getId()));
            }
            throw new ServiceException("没有需要修改的内容");
        }
        requireVersion(errorCategory.getVersion());
        if (DISABLED.equals(errorCategory.getStatus()))
        {
            return updateWithDisableCheck(errorCategory);
        }

        ErrorCategory snapshot = requireCategory(errorCategory.getId());
        validateSystemUnchanged(errorCategory, snapshot);
        requireCurrentVersion(errorCategory.getVersion(), snapshot.getVersion());
        boolean enabling = ENABLED.equals(errorCategory.getStatus())
                && !ENABLED.equals(snapshot.getStatus());
        if (enabling)
        {
            return updateWithEnableCheck(errorCategory, snapshot.getSystemId());
        }
        return executeUpdate(errorCategory);
    }

    /** 停用类别时直接锁类别，避免普通快照导致后续引用检查读到旧数据。 */
    private int updateWithDisableCheck(ErrorCategory errorCategory)
    {
        List<ErrorCategory> lockedCategories = errorCategoryMapper.selectErrorCategoriesByIdsForUpdate(
                new Long[] { errorCategory.getId() });
        if (lockedCategories.isEmpty())
        {
            throw new ServiceException("类别不存在或已删除");
        }
        ErrorCategory existing = lockedCategories.get(0);
        validateSystemUnchanged(errorCategory, existing);
        requireCurrentVersion(errorCategory.getVersion(), existing.getVersion());
        if (DISABLED.equals(existing.getStatus()))
        {
            return executeUpdate(errorCategory);
        }
        if (errorCodeMapper.countEnabledErrorCodesByCategoryIds(
                new Long[] { errorCategory.getId() }) > 0)
        {
            throw new ServiceException("类别下存在启用的错误码，请先停用相关错误码");
        }
        return executeUpdate(errorCategory);
    }

    /** 启用类别时按系统、类别的固定顺序加锁，并检查父系统状态。 */
    private int updateWithEnableCheck(ErrorCategory errorCategory, Long systemId)
    {
        ErrorSystem system = requireLockedSystem(systemId);
        List<ErrorCategory> lockedCategories = errorCategoryMapper.selectErrorCategoriesByIdsForUpdate(
                new Long[] { errorCategory.getId() });
        if (lockedCategories.isEmpty())
        {
            throw new ServiceException("类别不存在或已删除");
        }
        ErrorCategory existing = lockedCategories.get(0);
        validateSystemUnchanged(errorCategory, existing);
        if (!ENABLED.equals(system.getStatus()))
        {
            throw new ServiceException("所属系统未启用，不能启用该类别");
        }
        requireCurrentVersion(errorCategory.getVersion(), existing.getVersion());
        return executeUpdate(errorCategory);
    }

    private ErrorCategory requireCategory(Long id)
    {
        ErrorCategory category = errorCategoryMapper.selectErrorCategoryById(id);
        if (category == null)
        {
            throw new ServiceException("类别不存在或已删除");
        }
        return category;
    }

    // 禁止修改类别所属系统
    // 虽然更新的sql里根本没写改system，即使改了也不会改掉，前端里万一改了能提示到
    private void validateSystemUnchanged(ErrorCategory request, ErrorCategory existing)
    {
        if (request.getSystemId() != null
                && !existing.getSystemId().equals(request.getSystemId()))
        {
            throw new ServiceException("类别所属系统不允许修改");
        }
    }

    private int executeUpdate(ErrorCategory errorCategory)
    {
        int rows = errorCategoryMapper.updateErrorCategory(errorCategory);
        if (rows == 0)
        {
            throw new ServiceException("数据不存在、已删除或已被其他用户修改，请刷新后重试");
        }
        return rows;
    }

    @Override
    @Transactional
    public int updateErrorCategoryBatch(List<ErrorCategory> errorCategories)
    {
        int rows = 0;
        for (ErrorCategory errorCategory : BatchUpdateSupport.prepare(errorCategories, ErrorCategory::getId))
        {
            rows += updateErrorCategory(errorCategory);
        }
        return rows;
    }

    /**
     * 批量删除错误码类别
     * 
     * @param ids 需要删除的错误码类别主键
     * @return 结果
     */
    @Override
    @Transactional
    public int deleteErrorCategoryByIds(Long[] ids)
    {
        if (ids == null || ids.length == 0)
        {
            return 0;
        }
        errorCategoryMapper.selectErrorCategoriesByIdsForUpdate(ids);
        if (errorCodeMapper.countErrorCodesByCategoryIds(ids) > 0)
        {
            throw new ServiceException("所选类别下存在错误码，不能删除");
        }
        return errorCategoryMapper.deleteErrorCategoryByIds(ids);
    }

    /**
     * 删除错误码类别信息
     * 
     * @param id 错误码类别主键
     * @return 结果
     */
    @Override
    @Transactional
    public int deleteErrorCategoryById(Long id)
    {
        return deleteErrorCategoryByIds(new Long[] { id });
    }

    private ErrorSystem requireLockedSystem(Long systemId)
    {
        if (systemId == null)
        {
            throw new ServiceException("所属系统不能为空");
        }
        List<ErrorSystem> systems = errorSystemMapper.selectErrorSystemsByIdsForUpdate(
                new Long[] { systemId });
        if (systems.isEmpty())
        {
            throw new ServiceException("所属系统不存在或已删除");
        }
        return systems.get(0);
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
