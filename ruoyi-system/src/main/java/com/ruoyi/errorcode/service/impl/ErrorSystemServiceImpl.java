package com.ruoyi.errorcode.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
    @Autowired
    private ErrorSystemMapper errorSystemMapper;

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
        errorSystem.setCreateTime(DateUtils.getNowDate());
        return errorSystemMapper.insertErrorSystem(errorSystem);
    }

    /**
     * 修改错误码所属系统
     * 
     * @param errorSystem 错误码所属系统
     * @return 结果
     */
    @Override
    public int updateErrorSystem(ErrorSystem errorSystem)
    {
        errorSystem.setUpdateTime(DateUtils.getNowDate());
        return errorSystemMapper.updateErrorSystem(errorSystem);
    }

    /**
     * 批量删除错误码所属系统
     * 
     * @param ids 需要删除的错误码所属系统主键
     * @return 结果
     */
    @Override
    public int deleteErrorSystemByIds(Long[] ids)
    {
        return errorSystemMapper.deleteErrorSystemByIds(ids);
    }

    /**
     * 删除错误码所属系统信息
     * 
     * @param id 错误码所属系统主键
     * @return 结果
     */
    @Override
    public int deleteErrorSystemById(Long id)
    {
        return errorSystemMapper.deleteErrorSystemById(id);
    }
}
