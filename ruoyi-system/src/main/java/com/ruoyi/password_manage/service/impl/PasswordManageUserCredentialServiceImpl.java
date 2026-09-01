package com.ruoyi.password_manage.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.password_manage.domain.PasswordManageUserCredential;
import com.ruoyi.password_manage.mapper.PasswordManageUserCredentialMapper;
import com.ruoyi.password_manage.service.IPasswordManageUserCredentialService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 用户密码管理子，绑定password_manage_userService业务层处理
 * 
 * @author ruoyi
 * @date 2026-08-26
 */
@Service
public class PasswordManageUserCredentialServiceImpl implements IPasswordManageUserCredentialService {
    @Autowired
    private PasswordManageUserCredentialMapper passwordManageUserCredentialMapper;

    /**
     * 查询用户密码管理子，绑定password_manage_user
     * 
     * @param id 用户密码管理子，绑定password_manage_user主键
     * @return 用户密码管理子，绑定password_manage_user
     */
    @Override
    public PasswordManageUserCredential selectPasswordManageUserCredentialById(Long id) {
        return passwordManageUserCredentialMapper.selectPasswordManageUserCredentialById(id);
    }

    /**
     * 查询用户密码管理子，绑定password_manage_user列表
     * 
     * @param passwordManageUserCredential 用户密码管理子，绑定password_manage_user
     * @return 用户密码管理子，绑定password_manage_user
     */
    @Override
    public List<PasswordManageUserCredential> selectPasswordManageUserCredentialList(
            PasswordManageUserCredential passwordManageUserCredential) {
        return passwordManageUserCredentialMapper.selectPasswordManageUserCredentialList(passwordManageUserCredential);
    }

    /**
     * 新增用户密码管理子，绑定password_manage_user
     * 
     * @param passwordManageUserCredential 用户密码管理子，绑定password_manage_user
     * @return 结果
     */
    @Override
    public int insertPasswordManageUserCredential(PasswordManageUserCredential passwordManageUserCredential) {
        passwordManageUserCredential.setCreateTime(DateUtils.getNowDate());
        return passwordManageUserCredentialMapper.insertPasswordManageUserCredential(passwordManageUserCredential);
    }

    /**
     * 修改用户密码管理子，绑定password_manage_user
     * 
     * @param passwordManageUserCredential 用户密码管理子，绑定password_manage_user
     * @return 结果
     */
    @Override
    public int updatePasswordManageUserCredential(PasswordManageUserCredential passwordManageUserCredential) {
        return passwordManageUserCredentialMapper.updatePasswordManageUserCredential(passwordManageUserCredential);
    }

    /**
     * 批量删除用户密码管理子，绑定password_manage_user
     * 
     * @param ids 需要删除的用户密码管理子，绑定password_manage_user主键
     * @return 结果
     */
    @Override
    public int deletePasswordManageUserCredentialByIds(Long[] ids) {
        return passwordManageUserCredentialMapper.deletePasswordManageUserCredentialByIds(ids);
    }

    /**
     * 删除用户密码管理子，绑定password_manage_user信息
     * 
     * @param id 用户密码管理子，绑定password_manage_user主键
     * @return 结果
     */
    @Override
    public int deletePasswordManageUserCredentialById(Long id) {
        return passwordManageUserCredentialMapper.deletePasswordManageUserCredentialById(id);
    }
}
