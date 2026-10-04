package com.ruoyi.password_manage.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
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
import com.ruoyi.password_manage.domain.PasswordManageTeamRole;
import com.ruoyi.password_manage.domain.dto.PasswordManageTeamRoleDto;
import com.ruoyi.password_manage.domain.vo.TeamMemberVo;
import com.ruoyi.password_manage.service.IPasswordManageTeamRoleService;
import com.ruoyi.password_manage.service.PasswordManageUserService;
import com.ruoyi.password_manage.utils.TeamRoleVerifyUtils;
import io.swagger.v3.oas.annotations.Operation;
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

        /**
         * 查询某团队所有成员
         * 
         * @param id 团队id
         * @return 团队内所有成员列表
         */
        // @PreAuthorize("@ss.hasPermi('password_manage:role:list')")
        @GetMapping("/list")
        @Operation(summary = "获取团队成员列表", description = "获取团队成员列表")
        public TableDataInfo list(
                        @Parameter(name = "团队id", in = ParameterIn.QUERY) @RequestParam Long teamId) {
                // Long passwordManageUserId = passwordManageUserService
                // .selectPasswordManageUserByUserId(getLoginUser().getUserId()).getId();
                // Integer role = passwordManageTeamRoleService.selectTeamRoleOfMember(teamId,
                // passwordManageUserId);
                // if (role == null) {
                // throw new ServiceException("非团队成员，无团队成员列表查看权限。");
                // }
                teamRoleVerifyUtils.teamMemberVerify(teamId);
                startPage();
                List<TeamMemberVo> list = passwordManageTeamRoleService.selectTeamMemberList(teamId);
                return getDataTable(list);
        }

        /**
         * 新增团队成员
         * 
         */
        // @PreAuthorize("@ss.hasPermi('password_manage:role:add')")
        @Log(title = "新增团队成员", businessType = BusinessType.INSERT)
        @Operation(summary = "新增团队成员", description = "新增团队成员时，首次调用该方法，方法返回创建人金库秘钥密文及被添加人公钥；" +
                        "再次调用该方法，需传入被添加成员加密后金库秘钥")
        @PostMapping
        public AjaxResult add(@RequestBody PasswordManageTeamRoleDto dto) {
                Long memberId = dto.getUserId();
                Long teamId = dto.getTeamId();
                if (memberId == null) {
                        return AjaxResult.error("请选择要添加的成员");
                }
                // Long passwordManageUserId = passwordManageUserService
                // .selectPasswordManageUserByUserId(getLoginUser().getUserId()).getId();
                // PasswordManageTeamRole role = passwordManageTeamRoleService
                // .selectPasswordManageTeamRoleByPasswordManageUserId(teamId,
                // passwordManageUserId);
                // if (role == null || (TeamRole.SUPER_ADMIN != role.getTeamRole()
                // && TeamRole.ADMIN != role.getTeamRole())) {
                // throw new RuntimeException("非团队管理员，无团队成员管理权限。");
                // }
                List<Object> tmpList = teamRoleVerifyUtils.teamManagerVerify(teamId);
                Long passwordManageUserId = (Long) tmpList.get(0);
                return success(passwordManageTeamRoleService.addTeamMember(dto, passwordManageUserId));
        }

        /**
         * 获取可添加的成员候选列表（有个人金库、且尚未加入该团队的用户）
         */
        // @PreAuthorize("@ss.hasPermi('password_manage:role:add')")
        @Operation(summary = "获取可添加的成员候选列表")
        @GetMapping("/candidates")
        public AjaxResult candidates(
                        @Parameter(name = "团队id", in = ParameterIn.QUERY) @RequestParam Long teamId) {
                // Long passwordManageUserId = passwordManageUserService
                // .selectPasswordManageUserByUserId(getLoginUser().getUserId()).getId();
                // PasswordManageTeamRole role = passwordManageTeamRoleService
                // .selectPasswordManageTeamRoleByPasswordManageUserId(teamId,
                // passwordManageUserId);
                // if (role == null || (TeamRole.SUPER_ADMIN != role.getTeamRole()
                // && TeamRole.ADMIN != role.getTeamRole())) {
                // throw new RuntimeException("非团队管理员，无团队成员管理权限。");
                // }
                teamRoleVerifyUtils.teamManagerVerify(teamId);
                return AjaxResult.success(passwordManageTeamRoleService.selectCandidateMembers(teamId));
        }

        /***
         * 删除团队中某成员
         * 
         * @param teamId        操作团队id
         * @param deletedUserId 被删除团队成员的password_manage_user表id
         * @return
         */
        // @PreAuthorize("@ss.hasPermi('password_manage:role:remove')")
        @Log(title = "删除团队中某位成员", businessType = BusinessType.DELETE)
        @Operation(summary = "删除团队成员")
        @DeleteMapping
        public AjaxResult remove(@Parameter(name = "操作团队id") @RequestParam Long teamId,
                        @Parameter(name = "被删除队员的password_manage_user id") @RequestParam Long deletedUserId) {
                // Long passwordManageUserId = passwordManageUserService
                // .selectPasswordManageUserByUserId(getLoginUser().getUserId()).getId();
                // PasswordManageTeamRole role = passwordManageTeamRoleService
                // .selectPasswordManageTeamRoleByPasswordManageUserId(teamId,
                // passwordManageUserId);
                // if (role == null || (TeamRole.SUPER_ADMIN != role.getTeamRole()
                // && TeamRole.ADMIN != role.getTeamRole())) {
                // throw new RuntimeException("非团队管理员，无团队成员管理权限。");
                // }
                teamRoleVerifyUtils.teamManagerVerify(teamId);
                return toAjax(passwordManageTeamRoleService.deletePasswordManageTeamRoleById(teamId, deletedUserId));
        }

        /**
         * 团队成员退出团队
         */
        @DeleteMapping("/leave")
        public AjaxResult LeaveTeam(@RequestParam Long teamId) {
                Long passwordManageUserId = passwordManageUserService
                                .selectPasswordManageUserByUserId(getLoginUser().getUserId()).getId();
                return success(passwordManageTeamRoleService.MemberLeaveTeam(teamId, passwordManageUserId));
        }

        /**
         * 设置团队管理员权限
         * 
         * @param teamId
         * @param memberUserId 被赋予管理员权限成员的password_manage_user_id
         * @return
         */
        @PostMapping("/setTeamManager")
        public AjaxResult setTeamManagerAuth(@RequestParam Long teamId, @RequestParam Long memberUserId) {
                // Long passwordManageUserId = passwordManageUserService
                // .selectPasswordManageUserByUserId(getLoginUser().getUserId()).getId();
                // Integer role = passwordManageTeamRoleService.selectTeamRoleOfMember(teamId,
                // passwordManageUserId);
                // if (role == null || TeamRole.SUPER_ADMIN != role) {
                // throw new RuntimeException("非团队超级管理员，无权限设置团队管理员。");
                // }
                teamRoleVerifyUtils.teamSuperManagerVerify(teamId);
                return AjaxResult.success(
                                passwordManageTeamRoleService.setTeamManagerAuth(teamId, memberUserId));
        }

        /**
         * 移除团队管理员权限
         * 
         * @param teamId
         * @param managerUserId
         * @return
         */
        @PostMapping("/removeTeamManager")
        public AjaxResult removeTeamManagerAuth(@RequestParam Long teamId, @RequestParam Long managerUserId) {
                // Long passwordManageUserId = passwordManageUserService
                // .selectPasswordManageUserByUserId(getLoginUser().getUserId()).getId();
                // Integer role = passwordManageTeamRoleService.selectTeamRoleOfMember(teamId,
                // passwordManageUserId);
                // if (role == null || TeamRole.SUPER_ADMIN != role) {
                // throw new RuntimeException("非团队超级管理员，无权限设置团队管理员。");
                // }
                teamRoleVerifyUtils.teamSuperManagerVerify(teamId);
                return success(passwordManageTeamRoleService.removeTeamManagerAuth(teamId, managerUserId));
        }

        /**
         * 移交团队超级管理员权限
         * 
         * @param teamId
         * @param passowrdManageUserId
         * @return
         */
        @PostMapping("/transTeamSuperManager")
        public AjaxResult transTeamSuperManagerAuth(@RequestParam Long teamId, @RequestParam Long memberUserId) {
                // Long passwordManageUserId = passwordManageUserService
                // .selectPasswordManageUserByUserId(getLoginUser().getUserId()).getId();
                // Integer role = passwordManageTeamRoleService.selectTeamRoleOfMember(teamId,
                // passwordManageUserId);
                // if (role == null || TeamRole.SUPER_ADMIN != role) {
                // throw new RuntimeException("非团队超级管理员，无权限执行此操作。");
                // }
                List<Object> tmpList = teamRoleVerifyUtils.teamSuperManagerVerify(teamId);
                Long passwordManageUserId = (Long) tmpList.get(0);
                return success(passwordManageTeamRoleService.transTeamSuperManagerAuth(teamId, memberUserId,
                                passwordManageUserId));
        }

        /**
         * 获取当前登录用户在某团队中的角色信息
         * 
         * @param teamId
         * @return
         */
        @GetMapping("/loginUserRoleInfo")
        public AjaxResult getLoginUserPasswordManageTeamRoleInfo(@RequestParam Long teamId) {
                List<Object> list = teamRoleVerifyUtils.teamMemberVerify(teamId);
                return success((PasswordManageTeamRole) list.get(1));

        }
}
