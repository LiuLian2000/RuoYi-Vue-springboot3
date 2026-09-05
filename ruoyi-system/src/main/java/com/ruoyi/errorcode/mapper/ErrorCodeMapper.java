package com.ruoyi.errorcode.mapper;

import java.util.List;
import com.ruoyi.errorcode.domain.ErrorCode;

/**
 * 错误码Mapper接口
 * 
 * @author ruoyi
 * @date 2026-08-26
 */
public interface ErrorCodeMapper 
{
    /**
     * 查询错误码
     * 
     * @param id 错误码主键
     * @return 错误码
     */
    public ErrorCode selectErrorCodeById(Long id);

    /**
     * 查询错误码列表
     * 
     * @param errorCode 错误码
     * @return 错误码集合
     */
    public List<ErrorCode> selectErrorCodeList(ErrorCode errorCode);

    /**
     * 查询可导出的有效错误码及其系统、类别信息。
     *
     * @return 有效错误码集合
     */
    public List<ErrorCode> selectEnabledErrorCodeExportList();

    /**
     * 新增错误码
     * 
     * @param errorCode 错误码
     * @return 结果
     */
    public int insertErrorCode(ErrorCode errorCode);

    /**
     * 修改错误码
     * 
     * @param errorCode 错误码
     * @return 结果
     */
    public int updateErrorCode(ErrorCode errorCode);

    /**
     * 删除错误码
     * 
     * @param id 错误码主键
     * @return 结果
     */
    public int deleteErrorCodeById(Long id);

    /**
     * 批量删除错误码
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteErrorCodeByIds(Long[] ids);

    /** 查询指定系统下未删除错误码数量。 */
    public int countErrorCodesBySystemIds(Long[] systemIds);

    /** 查询指定类别下未删除错误码数量。 */
    public int countErrorCodesByCategoryIds(Long[] categoryIds);

    /** 查询引用指定严重程度ID的未删除错误码数量。 */
    public int countErrorCodesBySeverityIds(Long[] severityIds);

    /** 查询指定系统下启用且未删除的错误码数量。 */
    public int countEnabledErrorCodesBySystemIds(Long[] systemIds);

    /** 查询指定类别下启用且未删除的错误码数量。 */
    public int countEnabledErrorCodesByCategoryIds(Long[] categoryIds);

    /** 查询引用指定严重程度ID的启用且未删除错误码数量。 */
    public int countEnabledErrorCodesBySeverityIds(Long[] severityIds);
}
