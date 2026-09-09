package com.ruoyi.password_manage.controller;

import java.util.List;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.bean.BeanUtils;
import com.ruoyi.password_manage.domain.PasswordManageTeam;
import com.ruoyi.password_manage.domain.PasswordManageTeamRole;
import com.ruoyi.password_manage.domain.vo.PasswordManageTeamRoleVo;
import com.ruoyi.password_manage.service.IPasswordManageTeamRoleService;
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

    /**
     * 查询某团队所有成员
     * 
     * @param id 团队id
     * @return 团队内所有成员列表
     */
    @PreAuthorize("@ss.hasPermi('password_manage:role:list')")
    @GetMapping("/list")
    @Operation(summary = "获取团队成员列表", description = "获取团队成员列表")
    public TableDataInfo list(
            @Parameter(name = "团队id", in = ParameterIn.QUERY) @RequestParam Long teamId,
            @Parameter(name = "操作人id", in = ParameterIn.QUERY) @RequestParam Long userId) {
        Integer role = passwordManageTeamRoleService.selectTeamRoleOfMember(teamId, userId);
        if (role == null || 0 != role) {
            TableDataInfo tableDataInfo = new TableDataInfo();
            tableDataInfo.setCode(500);
            tableDataInfo.setMsg("非团队管理员，无团队成员列表查看权限。");
            return tableDataInfo;
        }
        startPage();
        List<SysUser> list = passwordManageTeamRoleService.selectPasswordManageTeamRoleList(teamId);
        return getDataTable(list);
    }

    /**
     * 新增团队成员
     */
    @PreAuthorize("@ss.hasPermi('password_manage:role:add')")
    @Log(title = "新增团队成员", businessType = BusinessType.INSERT)
    @Operation(summary = "新增团队成员", description = "仅限管理员进行操作")
    @PostMapping
    public AjaxResult add(@RequestBody PasswordManageTeamRoleVo vo) {
        Long operatorId = vo.getOperatedUserId();
        Long memberId = vo.getUserId();
        Long teamId = vo.getOperatedTeamId();
        if (memberId == null) {
            return AjaxResult.error("请选择要添加的成员");
        }
        Integer role = passwordManageTeamRoleService.selectTeamRoleOfMember(teamId, operatorId);
        if (role == null || 0 != role) {
            return AjaxResult.error("非团队管理员，无成员管理权限。");
        }
        PasswordManageTeamRole passwordManageTeamRole = new PasswordManageTeamRole();
        BeanUtils.copyProperties(vo, passwordManageTeamRole);
        passwordManageTeamRole.setTeamId(teamId);
        passwordManageTeamRole.setUserId(memberId);
        passwordManageTeamRole.setTeamRole(1);
        return toAjax(passwordManageTeamRoleService.insertPasswordManageTeamRole(passwordManageTeamRole));
    }

    /**
     * 获取可添加的成员候选列表（有个人金库、且尚未加入该团队的用户）
     */
    @PreAuthorize("@ss.hasPermi('password_manage:role:add')")
    @Operation(summary = "获取可添加的成员候选列表")
    @GetMapping("/candidates")
    public AjaxResult candidates(
            @Parameter(name = "团队id", in = ParameterIn.QUERY) @RequestParam Long teamId) {
        return AjaxResult.success(passwordManageTeamRoleService.selectCandidateMembers(teamId));
    }

    /**
     * 查询某用户在指定团队中的角色（0-admin，1-member），非成员返回 null
     */
    @PreAuthorize("@ss.hasPermi('password_manage:role:list')")
    @Operation(summary = "查询用户在团队中的角色")
    @GetMapping("/roleOfMember")
    public AjaxResult roleOfMember(
            @Parameter(name = "团队id") @RequestParam Long teamId,
            @Parameter(name = "操作人的password_manage_user id") @RequestParam Long userId) {
        return AjaxResult.success(passwordManageTeamRoleService.selectTeamRoleOfMember(teamId, userId));
    }

    /***
     * 删除团队中某成员
     * 
     * @param teamId        操作团队id
     * @param deletedUserId 被删除团队成员的password_manage_user表id
     * @param userId        操作人的password_manage_user表id
     * @return
     */
    @PreAuthorize("@ss.hasPermi('password_manage:role:remove')")
    @Log(title = "删除团队中某位成员", businessType = BusinessType.DELETE)
    @Operation(summary = "删除团队成员")
    @DeleteMapping("/remove")
    public AjaxResult remove(@Parameter(name = "操作团队id") @RequestParam Long teamId,
            @Parameter(name = "被删除队员的password_manage_user id") @RequestParam Long deletedUserId,
            @Parameter(name = "操作人的password_manage_user id") @RequestParam Long userId) {
        Integer role = passwordManageTeamRoleService.selectTeamRoleOfMember(teamId, userId);
        if (role == null || 0 != role) {
            return AjaxResult.error("非操作团队管理员，无该团队成员管理权限。");
        }
        return toAjax(passwordManageTeamRoleService.deletePasswordManageTeamRoleById(teamId, deletedUserId));
    }

    /**
     * 获取个人所在的团队列表
     */
    @PreAuthorize("@ss.hasPermi('password_manage:role:team_list')")
    @Operation(summary = "获取个人所属的团队列表")
    @GetMapping("/MyTeam/{id}")
    public TableDataInfo getTeamList(@Parameter(name = "操作人的password_manage_user id") @PathVariable("id") Long userId) {
        startPage();
        List<PasswordManageTeam> list = passwordManageTeamRoleService.selectPasswordManageTeamList(userId);
        return getDataTable(list);
    }
}
