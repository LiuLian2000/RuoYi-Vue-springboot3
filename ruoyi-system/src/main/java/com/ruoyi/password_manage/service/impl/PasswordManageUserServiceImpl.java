package com.ruoyi.password_manage.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ruoyi.password_manage.domain.PasswordManageUser;
import com.ruoyi.password_manage.mapper.PasswordManageUserMapper;
import com.ruoyi.password_manage.service.PasswordManageUserService;

/**
 * 用户密码管理主，绑定系统的sys_userService业务层处理
 * 
 * @author ruoyi
 * @date 2026-08-26
 */
@Service
@Transactional
public class PasswordManageUserServiceImpl implements PasswordManageUserService {
    @Autowired
    private PasswordManageUserMapper passwordManageUserMapper;

    /**
     * 查询用户密码管理主，绑定系统的sys_user
     * 
     * @param id 用户密码管理主，绑定系统的sys_user主键
     * @return 用户密码管理主，绑定系统的sys_user
     */
    @Override
    public PasswordManageUser selectPasswordManageUserById(Long id) {
        return passwordManageUserMapper.selectPasswordManageUserById(id);
    }

    /**
     * 查询用户ValutKey
     * 
     * @param usrId sys_user中的id主键
     * @return
     */
    public PasswordManageUser selectPasswordManageUserByUserId(Long userId) {
        return passwordManageUserMapper.selectPasswordManageUserByUserId(userId);
    }

    // /**
    // * 查询系统某id系统用户下的ValutKey记录的id
    // *
    // * @param userId
    // * @return
    // */
    // public int selectIdByUserId(Long userId){

    // }

    /**
     * 查询用户密码管理主，绑定系统的sys_user列表
     * 
     * @param passwordManageUser 用户密码管理主，绑定系统的sys_user
     * @return 用户密码管理主，绑定系统的sys_user
     */
    @Override
    public List<PasswordManageUser> selectPasswordManageUserList(PasswordManageUser passwordManageUser) {
        return passwordManageUserMapper.selectPasswordManageUserList(passwordManageUser);
    }

    /**
     * 新增用户密码管理主，绑定系统的sys_user
     * 
     * @param passwordManageUser 用户密码管理主，绑定系统的sys_user
     * @return 结果
     */
    @Override
    public int insertPasswordManageUser(PasswordManageUser passwordManageUser) {
        return passwordManageUserMapper.insertPasswordManageUser(passwordManageUser);
    }

    /**
     * 修改用户密码管理主，绑定系统的sys_user
     * 
     * @param passwordManageUser 用户密码管理主，绑定系统的sys_user
     * @return 结果
     */
    @Override
    public int updatePasswordManageUser(PasswordManageUser passwordManageUser) {
        return passwordManageUserMapper.updatePasswordManageUser(passwordManageUser);
    }

    /**
     * 批量删除用户密码管理主，绑定系统的sys_user
     * 
     * @param ids 需要删除的用户密码管理主，绑定系统的sys_user主键
     * @return 结果
     */
    @Override
    public int deletePasswordManageUserByIds(Long[] ids) {
        return passwordManageUserMapper.deletePasswordManageUserByIds(ids);
    }

    /**
     * 删除用户密码管理主，绑定系统的sys_user信息
     * 
     * @param id 用户密码管理主，绑定系统的sys_user主键
     * @return 结果
     */
    @Override
    public int deletePasswordManageUserById(Long id) {
        return passwordManageUserMapper.deletePasswordManageUserById(id);
    }
}
