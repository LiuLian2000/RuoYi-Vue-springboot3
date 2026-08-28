package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.system.domain.PasswordManageUser;

/**
 * 用户密码管理主，绑定系统的sys_userService接口
 * 
 * @author ruoyi
 * @date 2026-08-26
 */
public interface PasswordManageUserService {
    /**
     * 查询用户密码管理主，绑定系统的sys_user
     * 
     * @param id 用户密码管理主，绑定系统的sys_user主键
     * @return 用户密码管理主，绑定系统的sys_user
     */
    public PasswordManageUser selectPasswordManageUserById(Long id);

    /**
     * 查询系统用户的Valutkey
     * 
     * @param userId 对应Sys_user表中的主键id
     * @return
     */
    public PasswordManageUser selectPasswordManageUserByUserId(Long userId);

    // /**
    // * 查询系统某id系统用户下的ValutKey记录的id
    // *
    // * @param userId
    // * @return
    // */
    // public int selectIdByUserId(Long userId);

    /**
     * 查询用户密码管理主，绑定系统的sys_user列表
     * 
     * @param passwordManageUser 用户密码管理主，绑定系统的sys_user
     * @return 用户密码管理主，绑定系统的sys_user集合
     */
    public List<PasswordManageUser> selectPasswordManageUserList(PasswordManageUser passwordManageUser);

    /**
     * 新增用户密码管理主，绑定系统的sys_user
     * 
     * @param passwordManageUser 用户密码管理主，绑定系统的sys_user
     * @return 结果
     */
    public int insertPasswordManageUser(PasswordManageUser passwordManageUser);

    /**
     * 修改用户密码管理主，绑定系统的sys_user
     * 
     * @param passwordManageUser 用户密码管理主，绑定系统的sys_user
     * @return 结果
     */
    public int updatePasswordManageUser(PasswordManageUser passwordManageUser);

    /**
     * 批量删除用户密码管理主，绑定系统的sys_user
     * 
     * @param ids 需要删除的用户密码管理主，绑定系统的sys_user主键集合
     * @return 结果
     */
    public int deletePasswordManageUserByIds(Long[] ids);

    /**
     * 删除用户密码管理主，绑定系统的sys_user信息
     * 
     * @param id 用户密码管理主，绑定系统的sys_user主键
     * @return 结果
     */
    public int deletePasswordManageUserById(Long id);
}
