package com.ruoyi.errorcode.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.errorcode.mapper.ErrorCategoryMapper;
import com.ruoyi.errorcode.mapper.ErrorCodeMapper;
import com.ruoyi.errorcode.mapper.ErrorSystemMapper;
import com.ruoyi.errorcode.domain.ErrorSystem;
import com.ruoyi.errorcode.service.IErrorSystemService;

/**
 * 错误码所属系统Service业务层处理
 * 
 * @author ruoyi
 * @date 2026-08-26
 */
@Service
public class ErrorSystemServiceImpl implements IErrorSystemService 
{
    private static final Integer DISABLED = 0;

    @Autowired
    private ErrorSystemMapper errorSystemMapper;

    @Autowired
    private ErrorCategoryMapper errorCategoryMapper;

    @Autowired
    private ErrorCodeMapper errorCodeMapper;

    /**
     * 查询错误码所属系统
     * 
     * @param id 错误码所属系统主键
     * @return 错误码所属系统
     */
    @Override
    public ErrorSystem selectErrorSystemById(Long id)
    {
        return errorSystemMapper.selectErrorSystemById(id);
    }

    /**
     * 查询错误码所属系统列表
     * 
     * @param errorSystem 错误码所属系统
     * @return 错误码所属系统
     */
    @Override
    public List<ErrorSystem> selectErrorSystemList(ErrorSystem errorSystem)
    {
        return errorSystemMapper.selectErrorSystemList(errorSystem);
    }

    /**
     * 新增错误码所属系统
     * 
     * @param errorSystem 错误码所属系统
     * @return 结果
     */
    @Override
    public int insertErrorSystem(ErrorSystem errorSystem)
    {
        return errorSystemMapper.insertErrorSystem(errorSystem);
    }

    /**
     * 修改错误码所属系统
     * 
     * @param errorSystem 错误码所属系统
     * @return 结果
    */
    @Override
    @Transactional
    public int updateErrorSystem(ErrorSystem errorSystem)
    {
        if (errorSystem.getId() == null)
        {
            throw new ServiceException("系统ID不能为空");
        }
        // 没有修改内容会出现非法sql
        if (!hasText(errorSystem.getSystemCode())
                && !hasText(errorSystem.getSystemName())
                && errorSystem.getDescription() == null
                && errorSystem.getStatus() == null)
        {
            throw new ServiceException("没有需要修改的内容");
        }
        requireVersion(errorSystem.getVersion());
        // 如果要修改的目标不是停用状态，直接用乐观锁更新
        if (!DISABLED.equals(errorSystem.getStatus()))
        {
            return executeUpdate(errorSystem);
        }
        return updateWithDisableCheck(errorSystem);
    }

    /** 停用系统时锁定系统行，并在锁内检查所有启用下级。 */
    // 因为新增类别的时候要先查系统是否启用，启用才行，也用的系统的行锁，所以这里可以保证修改系统
    // 状态和新增类别不会出现并发问题。新增类别的行锁和这里的行锁是同一把锁。
    private int updateWithDisableCheck(ErrorSystem errorSystem)
    {
        List<ErrorSystem> lockedSystems = errorSystemMapper.selectErrorSystemsByIdsForUpdate(
                new Long[] { errorSystem.getId() });
        // 下面要用查的结果，判空顺手抛异常了
        // 查当前系统状态，看是不是从启用改为停用，如果是就要检查下级有没有启用的
        if (lockedSystems.isEmpty())
        {
            throw new ServiceException("系统不存在或已删除");
        }
        ErrorSystem existing = lockedSystems.get(0);

        if (DISABLED.equals(existing.getStatus()))
        {
            return executeUpdate(errorSystem);
        }
        // 为啥后面的executeUpdate也能查版本号，这里要先查
        // 防止提前返回存在未启用的类型，应该先查版本号，这个优先级高
        requireCurrentVersion(errorSystem.getVersion(), existing.getVersion());
        if (errorCategoryMapper.countEnabledErrorCategoriesBySystemIds(
                new Long[] { errorSystem.getId() }) > 0)
        {
            throw new ServiceException("系统下存在启用的类别，请先停用相关类别");
        }
        if (errorCodeMapper.countEnabledErrorCodesBySystemIds(
                new Long[] { errorSystem.getId() }) > 0)
        {
            throw new ServiceException("系统下存在启用的错误码，请先停用相关错误码");
        }
        return executeUpdate(errorSystem);
    }

    private int executeUpdate(ErrorSystem errorSystem)
    {
        int rows = errorSystemMapper.updateErrorSystem(errorSystem);
        if (rows == 0)
        {
            throw new ServiceException("数据不存在、已删除或已被其他用户修改，请刷新后重试");
        }
        return rows;
    }

    @Override
    @Transactional
    public int updateErrorSystemBatch(List<ErrorSystem> errorSystems)
    {
        int rows = 0;
        for (ErrorSystem errorSystem : BatchUpdateSupport.prepare(errorSystems, ErrorSystem::getId))
        {
            rows += updateErrorSystem(errorSystem);
        }
        return rows;
    }

    /**
     * 批量删除错误码所属系统
     * 
     * @param ids 需要删除的错误码所属系统主键
     * @return 结果
     */
    @Override
    @Transactional
    public int deleteErrorSystemByIds(Long[] ids)
    {
        if (ids == null || ids.length == 0)
        {
            return 0;
        }
        errorSystemMapper.selectErrorSystemsByIdsForUpdate(ids);
        if (errorCategoryMapper.countErrorCategoriesBySystemIds(ids) > 0)
        {
            throw new ServiceException("所选系统下存在类别，不能删除");
        }
        if (errorCodeMapper.countErrorCodesBySystemIds(ids) > 0)
        {
            throw new ServiceException("所选系统下存在错误码，不能删除");
        }
        return errorSystemMapper.deleteErrorSystemByIds(ids);
    }

    /**
     * 删除错误码所属系统信息
     * 
     * @param id 错误码所属系统主键
     * @return 结果
     */
    @Override
    @Transactional
    public int deleteErrorSystemById(Long id)
    {
        return deleteErrorSystemByIds(new Long[] { id });
    }

    private boolean hasText(String value)
    {
        return value != null && !value.isBlank();
    }

    private void requireVersion(Integer version)
    {
        if (version == null)
        {
            throw new ServiceException("当前页面数据已失效，请刷新后重试");
        }
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
