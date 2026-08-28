package com.ruoyi.errorcode.service;

import java.util.List;
import com.ruoyi.errorcode.domain.ErrorCategory;

/**
 * 错误码类别Service接口
 * 
 * @author ruoyi
 * @date 2026-08-26
 */
public interface IErrorCategoryService 
{
    /**
     * 查询错误码类别
     * 
     * @param id 错误码类别主键
     * @return 错误码类别
     */
    public ErrorCategory selectErrorCategoryById(Long id);

    /**
     * 查询错误码类别列表
     * 
     * @param errorCategory 错误码类别
     * @return 错误码类别集合
     */
    public List<ErrorCategory> selectErrorCategoryList(ErrorCategory errorCategory);

    /**
     * 新增错误码类别
     * 
     * @param errorCategory 错误码类别
     * @return 结果
     */
    public int insertErrorCategory(ErrorCategory errorCategory);

    /**
     * 修改错误码类别
     * 
     * @param errorCategory 错误码类别
     * @return 结果
     */
    public int updateErrorCategory(ErrorCategory errorCategory);

    /**
     * 批量删除错误码类别
     * 
     * @param ids 需要删除的错误码类别主键集合
     * @return 结果
     */
    public int deleteErrorCategoryByIds(Long[] ids);

    /**
     * 删除错误码类别信息
     * 
     * @param id 错误码类别主键
     * @return 结果
     */
    public int deleteErrorCategoryById(Long id);
}
