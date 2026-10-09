package com.ruoyi.password_manage.utils;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.password_manage.common.constants.ExceptionMessages;
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
            throw new ServiceException(ExceptionMessages.NOT_TEAM_MEM);
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
            throw new RuntimeException(ExceptionMessages.NOT_TEAM_ADMIN);
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
            throw new RuntimeException(ExceptionMessages.NOT_TEAM_SUPER_ADMIN);
        } else {
            return new ArrayList<>(List.of(passwordManageUserId, role));
        }
    }

}
