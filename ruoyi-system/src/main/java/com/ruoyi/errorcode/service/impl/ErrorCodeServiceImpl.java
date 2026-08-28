package com.ruoyi.errorcode.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.errorcode.mapper.ErrorCodeMapper;
import com.ruoyi.errorcode.domain.ErrorCode;
import com.ruoyi.errorcode.service.IErrorCodeService;

/**
 * 错误码Service业务层处理
 * 
 * @author ruoyi
 * @date 2026-08-26
 */
@Service
public class ErrorCodeServiceImpl implements IErrorCodeService 
{
    @Autowired
    private ErrorCodeMapper errorCodeMapper;

    /**
     * 查询错误码
     * 
     * @param id 错误码主键
     * @return 错误码
     */
    @Override
    public ErrorCode selectErrorCodeById(Long id)
    {
        return errorCodeMapper.selectErrorCodeById(id);
    }

    /**
     * 查询错误码列表
     * 
     * @param errorCode 错误码
     * @return 错误码
     */
    @Override
    public List<ErrorCode> selectErrorCodeList(ErrorCode errorCode)
    {
        return errorCodeMapper.selectErrorCodeList(errorCode);
    }

    /**
     * 新增错误码
     * 
     * @param errorCode 错误码
     * @return 结果
     */
    @Override
    public int insertErrorCode(ErrorCode errorCode)
    {
        errorCode.setCreateTime(DateUtils.getNowDate());
        return errorCodeMapper.insertErrorCode(errorCode);
    }

    /**
     * 修改错误码
     * 
     * @param errorCode 错误码
     * @return 结果
     */
    @Override
    public int updateErrorCode(ErrorCode errorCode)
    {
        errorCode.setUpdateTime(DateUtils.getNowDate());
        return errorCodeMapper.updateErrorCode(errorCode);
    }

    /**
     * 批量删除错误码
     * 
     * @param ids 需要删除的错误码主键
     * @return 结果
     */
    @Override
    public int deleteErrorCodeByIds(Long[] ids)
    {
        return errorCodeMapper.deleteErrorCodeByIds(ids);
    }

    /**
     * 删除错误码信息
     * 
     * @param id 错误码主键
     * @return 结果
     */
    @Override
    public int deleteErrorCodeById(Long id)
    {
        return errorCodeMapper.deleteErrorCodeById(id);
    }
}
