package com.ruoyi.errorcode.mapper;

import java.util.List;
import com.ruoyi.errorcode.domain.ErrorSeverity;

/**
 * 错误码严重程度Mapper接口
 * 
 * @author ruoyi
 * @date 2026-08-26
 */
public interface ErrorSeverityMapper 
{
    /**
     * 查询错误码严重程度
     * 
     * @param id 错误码严重程度主键
     * @return 错误码严重程度
     */
    public ErrorSeverity selectErrorSeverityById(Long id);

    /**
     * 查询错误码严重程度列表
     * 
     * @param errorSeverity 错误码严重程度
     * @return 错误码严重程度集合
     */
    public List<ErrorSeverity> selectErrorSeverityList(ErrorSeverity errorSeverity);

    /** 按主键排序锁定未删除严重程度。 */
    public List<ErrorSeverity> selectErrorSeveritiesByIdsForUpdate(Long[] ids);

    /**
     * 新增错误码严重程度
     * 
     * @param errorSeverity 错误码严重程度
     * @return 结果
     */
    public int insertErrorSeverity(ErrorSeverity errorSeverity);

    /**
     * 修改错误码严重程度
     * 
     * @param errorSeverity 错误码严重程度
     * @return 结果
     */
    public int updateErrorSeverity(ErrorSeverity errorSeverity);

    /**
     * 删除错误码严重程度
     * 
     * @param id 错误码严重程度主键
     * @return 结果
     */
    public int deleteErrorSeverityById(Long id);

    /**
     * 批量删除错误码严重程度
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteErrorSeverityByIds(Long[] ids);
}
