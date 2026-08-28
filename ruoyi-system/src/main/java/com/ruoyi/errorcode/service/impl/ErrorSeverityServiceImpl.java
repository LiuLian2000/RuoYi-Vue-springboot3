package com.ruoyi.errorcode.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
    @Autowired
    private ErrorSeverityMapper errorSeverityMapper;

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
        errorSeverity.setCreateTime(DateUtils.getNowDate());
        return errorSeverityMapper.insertErrorSeverity(errorSeverity);
    }

    /**
     * 修改错误码严重程度
     * 
     * @param errorSeverity 错误码严重程度
     * @return 结果
     */
    @Override
    public int updateErrorSeverity(ErrorSeverity errorSeverity)
    {
        errorSeverity.setUpdateTime(DateUtils.getNowDate());
        return errorSeverityMapper.updateErrorSeverity(errorSeverity);
    }

    /**
     * 批量删除错误码严重程度
     * 
     * @param ids 需要删除的错误码严重程度主键
     * @return 结果
     */
    @Override
    public int deleteErrorSeverityByIds(Long[] ids)
    {
        return errorSeverityMapper.deleteErrorSeverityByIds(ids);
    }

    /**
     * 删除错误码严重程度信息
     * 
     * @param id 错误码严重程度主键
     * @return 结果
     */
    @Override
    public int deleteErrorSeverityById(Long id)
    {
        return errorSeverityMapper.deleteErrorSeverityById(id);
    }
}
