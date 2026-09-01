package com.ruoyi.password_manage.mapper;

import java.util.List;

import com.ruoyi.password_manage.domain.PasswordManageUser;

/**
 * 用户密码管理主，绑定系统的sys_userMapper接口
 * 
 * @author ruoyi
 * @date 2026-08-26
 */
public interface PasswordManageUserMapper {
    /**
     * 查询用户密码管理主，绑定系统的sys_user
     * 
     * @param id 用户密码管理主，绑定系统的sys_user主键
     * @return 用户密码管理主，绑定系统的sys_user
     */
    public PasswordManageUser selectPasswordManageUserById(Long id);

    /**
     * 查询用户ValutKey
     * 
     * @param usrId sys_user中的id主键
     * @return
     */
    public PasswordManageUser selectPasswordManageUserByUserId(Long usrId);

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
     * 删除用户密码管理主，绑定系统的sys_user
     * 
     * @param id 用户密码管理主，绑定系统的sys_user主键
     * @return 结果
     */
    public int deletePasswordManageUserById(Long id);

    /**
     * 批量删除用户密码管理主，绑定系统的sys_user
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deletePasswordManageUserByIds(Long[] ids);
}
