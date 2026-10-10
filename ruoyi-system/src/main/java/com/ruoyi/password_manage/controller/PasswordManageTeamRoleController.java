package com.ruoyi.password_manage.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.password_manage.common.constants.ExceptionMessages;
import com.ruoyi.password_manage.domain.PasswordManageTeamRole;
import com.ruoyi.password_manage.domain.dto.PasswordManageTeamRoleDto;
import com.ruoyi.password_manage.domain.vo.TeamMemberVo;
import com.ruoyi.password_manage.service.IPasswordManageTeamRoleService;
import com.ruoyi.password_manage.service.PasswordManageUserService;
import com.ruoyi.password_manage.utils.TeamRoleVerifyUtils;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 用户在团队中的角色Controller
 * 
 * @author DiZhicong
 * @date 2026-09-02
 */
@RestController
@RequestMapping("/password_manage/role")
public class PasswordManageTeamRoleController extends BaseController {

        @Autowired
        private IPasswordManageTeamRoleService passwordManageTeamRoleService;

        @Autowired
        private PasswordManageUserService passwordManageUserService;

        @Autowired
        private TeamRoleVerifyUtils teamRoleVerifyUtils;

        private Logger log = LoggerFactory.getLogger(PasswordManageTeamController.class);

        /**
         * 查询某团队所有成员
         * 
         * @param id 团队id
         * @return 团队内所有成员列表
         */
        @PreAuthorize("@ss.hasPermi('system:passwordManage:operate')")
        @Log(title = "查询团队成员列表", businessType = BusinessType.QUERY)
        @GetMapping("/list")
        public TableDataInfo list(
                        @Parameter(name = "团队id", in = ParameterIn.QUERY) @RequestParam Long teamId) {

                try {
                        teamRoleVerifyUtils.teamMemberVerify(teamId);
                        startPage();
                        List<TeamMemberVo> list = passwordManageTeamRoleService.selectTeamMemberList(teamId);
                        return getDataTable(list);
                } catch (ServiceException se) {
                        throw se;
                } catch (Exception e) {
                        log.info(e.getMessage(), e);
                        throw new ServiceException(ExceptionMessages.NORMAL);
                }

        }

        /**
         * 新增团队成员
         * 
         */
        @PreAuthorize("@ss.hasPermi('system:passwordManage:operate')")
        @Log(title = "新增团队成员", businessType = BusinessType.INSERT)
        @PostMapping
        public AjaxResult add(@RequestBody PasswordManageTeamRoleDto dto) {
                try {
                        Long memberId = dto.getUserId();
                        Long teamId = dto.getTeamId();
                        if (memberId == null) {
                                return AjaxResult.error("请选择要添加的成员");
                        }
                        List<Object> tmpList = teamRoleVerifyUtils.teamManagerVerify(teamId);
                        Long passwordManageUserId = (Long) tmpList.get(0);
                        return success(passwordManageTeamRoleService.addTeamMember(dto, passwordManageUserId));
                } catch (ServiceException se) {
                        throw se;
                } catch (Exception e) {
                        log.info(e.getMessage(), e);
                        throw new ServiceException(ExceptionMessages.NORMAL);
                }

        }

        /**
         * 获取可添加的成员候选列表（有个人金库、且尚未加入该团队的用户）
         */
        @PreAuthorize("@ss.hasPermi('system:passwordManage:operate')")
        @Log(title = "查询团队可添加成员列表", businessType = BusinessType.QUERY)
        @GetMapping("/candidates")
        public AjaxResult candidates(
                        @Parameter(name = "团队id", in = ParameterIn.QUERY) @RequestParam Long teamId) {
                try {
                        teamRoleVerifyUtils.teamManagerVerify(teamId);
                        return AjaxResult.success(passwordManageTeamRoleService.selectCandidateMembers(teamId));
                } catch (ServiceException se) {
                        throw se;
                } catch (Exception e) {
                        log.info(e.getMessage(), e);
                        throw new ServiceException(ExceptionMessages.NORMAL);
                }

        }

        /***
         * 删除团队成员
         * 
         * @param teamId        操作团队id
         * @param deletedUserId 被删除团队成员的password_manage_user表id
         * @return
         */
        @PreAuthorize("@ss.hasPermi('system:passwordManage:operate')")
        @Log(title = "删除团队成员", businessType = BusinessType.DELETE)
        @DeleteMapping
        public AjaxResult remove(@Parameter(name = "操作团队id") @RequestParam Long teamId,
                        @Parameter(name = "被删除队员的password_manage_user id") @RequestParam Long deletedUserId) {
                try {
                        teamRoleVerifyUtils.teamManagerVerify(teamId);
                        return toAjax(passwordManageTeamRoleService.deletePasswordManageTeamRoleById(teamId,
                                        deletedUserId));
                } catch (ServiceException se) {
                        throw se;
                } catch (Exception e) {
                        log.info(e.getMessage(), e);
                        throw new ServiceException(ExceptionMessages.NORMAL);
                }

        }

        /**
         * 团队成员退出团队
         */
        @PreAuthorize("@ss.hasPermi('system:passwordManage:operate')")
        @Log(title = "退出团队", businessType = BusinessType.DELETE)
        @DeleteMapping("/leave")
        public AjaxResult LeaveTeam(@RequestParam Long teamId) {
                try {
                        Long passwordManageUserId = passwordManageUserService
                                        .selectPasswordManageUserByUserId(getLoginUser().getUserId()).getId();
                        return success(passwordManageTeamRoleService.MemberLeaveTeam(teamId, passwordManageUserId));
                } catch (ServiceException se) {
                        throw se;
                } catch (Exception e) {
                        log.info(e.getMessage(), e);
                        throw new ServiceException(ExceptionMessages.NORMAL);
                }

        }

        /**
         * 设置团队管理员权限
         * 
         * @param teamId
         * @param memberUserId 被赋予管理员权限成员的password_manage_user_id
         * @return
         */
        @PreAuthorize("@ss.hasPermi('system:passwordManage:operate')")
        @Log(title = "设置管理员权限", businessType = BusinessType.UPDATE)
        @PostMapping("/setTeamManager")
        public AjaxResult setTeamManagerAuth(@RequestParam Long teamId, @RequestParam Long memberUserId) {
                try {
                        teamRoleVerifyUtils.teamSuperManagerVerify(teamId);
                        return AjaxResult.success(
                                        passwordManageTeamRoleService.setTeamManagerAuth(teamId, memberUserId));
                } catch (ServiceException se) {
                        throw se;
                } catch (Exception e) {
                        log.info(e.getMessage(), e);
                        throw new ServiceException(ExceptionMessages.NORMAL);
                }

        }

        /**
         * 移除团队管理员权限
         * 
         * @param teamId
         * @param managerUserId
         * @return
         */
        @PreAuthorize("@ss.hasPermi('system:passwordManage:operate')")
        @Log(title = "移除管理员权限", businessType = BusinessType.UPDATE)
        @PostMapping("/removeTeamManager")
        public AjaxResult removeTeamManagerAuth(@RequestParam Long teamId, @RequestParam Long managerUserId) {
                try {
                        teamRoleVerifyUtils.teamSuperManagerVerify(teamId);
                        return success(passwordManageTeamRoleService.removeTeamManagerAuth(teamId, managerUserId));
                } catch (ServiceException se) {
                        throw se;
                } catch (Exception e) {
                        log.info(e.getMessage(), e);
                        throw new ServiceException(ExceptionMessages.NORMAL);
                }

        }

        /**
         * 移交团队超级管理员权限
         * 
         * @param teamId
         * @param passowrdManageUserId
         * @return
         */
        @PreAuthorize("@ss.hasPermi('system:passwordManage:operate')")
        @Log(title = "移交超级管理员权限", businessType = BusinessType.UPDATE)
        @PostMapping("/transTeamSuperManager")
        public AjaxResult transTeamSuperManagerAuth(@RequestParam Long teamId, @RequestParam Long memberUserId) {
                try {
                        List<Object> tmpList = teamRoleVerifyUtils.teamSuperManagerVerify(teamId);
                        Long passwordManageUserId = (Long) tmpList.get(0);
                        return success(passwordManageTeamRoleService.transTeamSuperManagerAuth(teamId, memberUserId,
                                        passwordManageUserId));
                } catch (ServiceException se) {
                        throw se;
                } catch (Exception e) {
                        log.info(e.getMessage(), e);
                        throw new ServiceException(ExceptionMessages.NORMAL);
                }

        }

        /**
         * 获取当前登录用户在某团队中的角色信息
         * 
         * @param teamId
         * @return
         */
        @PreAuthorize("@ss.hasPermi('system:passwordManage:operate')")
        @Log(title = "查询当前用户在团队的角色", businessType = BusinessType.QUERY)
        @GetMapping("/loginUserRoleInfo")
        public AjaxResult getLoginUserPasswordManageTeamRoleInfo(@RequestParam Long teamId) {
                try {
                        List<Object> list = teamRoleVerifyUtils.teamMemberVerify(teamId);
                        return success((PasswordManageTeamRole) list.get(1));
                } catch (ServiceException se) {
                        throw se;
                } catch (Exception e) {
                        log.info(e.getMessage(), e);
                        throw new ServiceException(ExceptionMessages.NORMAL);
                }

        }
}
