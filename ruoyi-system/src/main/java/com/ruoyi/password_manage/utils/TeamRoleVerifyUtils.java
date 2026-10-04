package com.ruoyi.password_manage.utils;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.password_manage.common.constants.TeamRole;
import com.ruoyi.password_manage.domain.PasswordManageTeamRole;
import com.ruoyi.password_manage.service.PasswordManageUserService;
import com.ruoyi.password_manage.service.impl.PasswordManageTeamRoleServiceImpl;

@Component
public class TeamRoleVerifyUtils {

    @Autowired
    PasswordManageUserService passwordManageUserService;

    @Autowired
    PasswordManageTeamRoleServiceImpl passwordManageTeamRoleService;

    /**
     * 检查当前用户是否为目标团队成员
     * 
     * @param teamId
     * @return
     */
    public List<Object> teamMemberVerify(Long teamId) {
        Long passwordManageUserId = passwordManageUserService
                .selectPasswordManageUserByUserId(SecurityUtils.getLoginUser().getUserId()).getId();
        PasswordManageTeamRole role = passwordManageTeamRoleService
                .selectPasswordManageTeamRoleByPasswordManageUserId(teamId, passwordManageUserId);
        if (role == null) {
            throw new ServiceException("非团队成员，无团队成员操作权限。");
        } else {
            return new ArrayList<>(List.of(passwordManageUserId, role));
        }
    }

    /**
     * 检查当前用户是否为目标团队管理员
     * 
     * @param teamId
     * @return
     */
    public List<Object> teamManagerVerify(Long teamId) {
        Long passwordManageUserId = passwordManageUserService
                .selectPasswordManageUserByUserId(SecurityUtils.getLoginUser().getUserId()).getId();
        PasswordManageTeamRole role = passwordManageTeamRoleService
                .selectPasswordManageTeamRoleByPasswordManageUserId(teamId, passwordManageUserId);
        if (role == null || (TeamRole.SUPER_ADMIN != role.getTeamRole()
                && TeamRole.ADMIN != role.getTeamRole())) {
            throw new RuntimeException("非团队管理员，无团队管理员操作权限。");
        } else {
            return new ArrayList<>(List.of(passwordManageUserId, role));
        }
    }

    /**
     * 检查当前用户是否为目标团队超级管理员
     * 
     * @param teamId
     * @return
     */
    public List<Object> teamSuperManagerVerify(Long teamId) {
        Long passwordManageUserId = passwordManageUserService
                .selectPasswordManageUserByUserId(SecurityUtils.getLoginUser().getUserId()).getId();
        PasswordManageTeamRole role = passwordManageTeamRoleService
                .selectPasswordManageTeamRoleByPasswordManageUserId(teamId, passwordManageUserId);
        if (role == null || TeamRole.SUPER_ADMIN != role.getTeamRole()) {
            throw new RuntimeException("非团队超级管理员，无团队超级管理员操作权限。");
        } else {
            return new ArrayList<>(List.of(passwordManageUserId, role));
        }
    }

}
