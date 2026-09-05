package com.ruoyi.errorcode.service;

import java.util.List;
import com.ruoyi.errorcode.domain.ErrorCode;

/**
 * 错误码Service接口
 * 
 * @author ruoyi
 * @date 2026-08-26
 */
public interface IErrorCodeService 
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

    /** 批量修改错误码，任意一条失败时整批回滚。 */
    public int updateErrorCodeBatch(List<ErrorCode> errorCodes);

    /**
     * 批量删除错误码
     * 
     * @param ids 需要删除的错误码主键集合
     * @return 结果
     */
    public int deleteErrorCodeByIds(Long[] ids);

    /**
     * 删除错误码信息
     * 
     * @param id 错误码主键
     * @return 结果
     */
    public int deleteErrorCodeById(Long id);
}
