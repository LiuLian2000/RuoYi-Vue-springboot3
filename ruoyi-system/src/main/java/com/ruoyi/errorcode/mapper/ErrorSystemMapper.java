package com.ruoyi.errorcode.mapper;

import java.util.List;
import com.ruoyi.errorcode.domain.ErrorSystem;

/**
 * 错误码所属系统Mapper接口
 * 
 * @author ruoyi
 * @date 2026-08-26
 */
public interface ErrorSystemMapper 
{
    /**
     * 查询错误码所属系统
     * 
     * @param id 错误码所属系统主键
     * @return 错误码所属系统
     */
    public ErrorSystem selectErrorSystemById(Long id);

    /**
     * 查询错误码所属系统列表
     * 
     * @param errorSystem 错误码所属系统
     * @return 错误码所属系统集合
     */
    public List<ErrorSystem> selectErrorSystemList(ErrorSystem errorSystem);

    /** 按主键排序锁定未删除系统。 */
    public List<ErrorSystem> selectErrorSystemsByIdsForUpdate(Long[] ids);

    /**
     * 新增错误码所属系统
     * 
     * @param errorSystem 错误码所属系统
     * @return 结果
     */
    public int insertErrorSystem(ErrorSystem errorSystem);

    /**
     * 修改错误码所属系统
     * 
     * @param errorSystem 错误码所属系统
     * @return 结果
     */
    public int updateErrorSystem(ErrorSystem errorSystem);

    /**
     * 删除错误码所属系统
     * 
     * @param id 错误码所属系统主键
     * @return 结果
     */
    public int deleteErrorSystemById(Long id);

    /**
     * 批量删除错误码所属系统
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteErrorSystemByIds(Long[] ids);
}
