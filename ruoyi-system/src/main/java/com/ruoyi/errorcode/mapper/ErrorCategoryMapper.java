package com.ruoyi.errorcode.mapper;

import java.util.List;
import com.ruoyi.errorcode.domain.ErrorCategory;

/**
 * 错误码类别Mapper接口
 * 
 * @author ruoyi
 * @date 2026-08-26
 */
public interface ErrorCategoryMapper 
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

    /** 按主键排序锁定未删除类别。 */
    public List<ErrorCategory> selectErrorCategoriesByIdsForUpdate(Long[] ids);

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
     * 删除错误码类别
     * 
     * @param id 错误码类别主键
     * @return 结果
     */
    public int deleteErrorCategoryById(Long id);

    /**
     * 批量删除错误码类别
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteErrorCategoryByIds(Long[] ids);

    /** 查询指定系统下未删除类别数量。 */
    public int countErrorCategoriesBySystemIds(Long[] systemIds);

    /** 查询指定系统下启用且未删除的类别数量。 */
    public int countEnabledErrorCategoriesBySystemIds(Long[] systemIds);
}
