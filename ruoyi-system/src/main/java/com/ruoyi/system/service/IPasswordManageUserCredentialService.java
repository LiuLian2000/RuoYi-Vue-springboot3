package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.system.domain.PasswordManageUserCredential;

/**
 * 用户密码管理子，绑定password_manage_userService接口
 * 
 * @author ruoyi
 * @date 2026-08-26
 */
public interface IPasswordManageUserCredentialService 
{
    /**
     * 查询用户密码管理子，绑定password_manage_user
     * 
     * @param id 用户密码管理子，绑定password_manage_user主键
     * @return 用户密码管理子，绑定password_manage_user
     */
    public PasswordManageUserCredential selectPasswordManageUserCredentialById(Long id);

    /**
     * 查询用户密码管理子，绑定password_manage_user列表
     * 
     * @param passwordManageUserCredential 用户密码管理子，绑定password_manage_user
     * @return 用户密码管理子，绑定password_manage_user集合
     */
    public List<PasswordManageUserCredential> selectPasswordManageUserCredentialList(PasswordManageUserCredential passwordManageUserCredential);

    /**
     * 新增用户密码管理子，绑定password_manage_user
     * 
     * @param passwordManageUserCredential 用户密码管理子，绑定password_manage_user
     * @return 结果
     */
    public int insertPasswordManageUserCredential(PasswordManageUserCredential passwordManageUserCredential);

    /**
     * 修改用户密码管理子，绑定password_manage_user
     * 
     * @param passwordManageUserCredential 用户密码管理子，绑定password_manage_user
     * @return 结果
     */
    public int updatePasswordManageUserCredential(PasswordManageUserCredential passwordManageUserCredential);

    /**
     * 批量删除用户密码管理子，绑定password_manage_user
     * 
     * @param ids 需要删除的用户密码管理子，绑定password_manage_user主键集合
     * @return 结果
     */
    public int deletePasswordManageUserCredentialByIds(Long[] ids);

    /**
     * 删除用户密码管理子，绑定password_manage_user信息
     * 
     * @param id 用户密码管理子，绑定password_manage_user主键
     * @return 结果
     */
    public int deletePasswordManageUserCredentialById(Long id);
}
