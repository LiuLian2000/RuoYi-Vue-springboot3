package com.ruoyi.errorcode.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.errorcode.mapper.ErrorCategoryMapper;
import com.ruoyi.errorcode.domain.ErrorCategory;
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
    @Autowired
    private ErrorCategoryMapper errorCategoryMapper;

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
    public int insertErrorCategory(ErrorCategory errorCategory)
    {
        errorCategory.setCreateTime(DateUtils.getNowDate());
        return errorCategoryMapper.insertErrorCategory(errorCategory);
    }

    /**
     * 修改错误码类别
     * 
     * @param errorCategory 错误码类别
     * @return 结果
     */
    @Override
    public int updateErrorCategory(ErrorCategory errorCategory)
    {
        errorCategory.setUpdateTime(DateUtils.getNowDate());
        return errorCategoryMapper.updateErrorCategory(errorCategory);
    }

    /**
     * 批量删除错误码类别
     * 
     * @param ids 需要删除的错误码类别主键
     * @return 结果
     */
    @Override
    public int deleteErrorCategoryByIds(Long[] ids)
    {
        return errorCategoryMapper.deleteErrorCategoryByIds(ids);
    }

    /**
     * 删除错误码类别信息
     * 
     * @param id 错误码类别主键
     * @return 结果
     */
    @Override
    public int deleteErrorCategoryById(Long id)
    {
        return errorCategoryMapper.deleteErrorCategoryById(id);
    }
}
