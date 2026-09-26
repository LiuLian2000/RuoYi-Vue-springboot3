package com.ruoyi.password_manage.controller;

import java.util.List;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.password_manage.domain.PasswordManageTeam;
import com.ruoyi.password_manage.domain.PasswordManageUser;
import com.ruoyi.password_manage.domain.vo.PasswordManageTeamVo;
import com.ruoyi.password_manage.service.IPasswordManageTeamRoleService;
import com.ruoyi.password_manage.service.IPasswordManageTeamService;
import com.ruoyi.password_manage.service.PasswordManageUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;

/**
 * 团队密码管理-团队密码Controller
 * 
 * @author DiZhicong
 * @date 2026-09-01
 */
@RestController
@RequestMapping("/password_manage/team")
public class PasswordManageTeamController extends BaseController {

    @Autowired
    private IPasswordManageTeamService passwordManageTeamService;

    @Autowired
    private IPasswordManageTeamRoleService passwordManageTeamRoleService;

    @Autowired
    private PasswordManageUserService passwordManageUserService;

    /**
     * 查询管理团队列表
     */
    @PreAuthorize("@ss.hasPermi('password_manage:team:list')")
    @Operation(summary = "查询管理团队列表", description = "团队管理员依赖此接口查询管理团队列表")
    @GetMapping("/ManageTeamlist")
    public AjaxResult getManageTeamlist() {
        startPage();
        Long sysusrId = getLoginUser().getUserId();
        Long passwordManageUserId = (passwordManageUserService.selectPasswordManageUserByUserId(sysusrId)).getId();
        List<PasswordManageTeam> list = passwordManageTeamService
                .selectManageTeamList(passwordManageUserId);
        return AjaxResult.success(list);
    }

    /**
     * 查询以成员身份所在的团队列表
     */
    public AjaxResult getTeamlist() {
        startPage();
        Long sysusrId = getLoginUser().getUserId();
        Long passwordManageUserId = (passwordManageUserService.selectPasswordManageUserByUserId(sysusrId)).getId();
        List<PasswordManageTeam> list = passwordManageTeamService
                .selectTeamList(passwordManageUserId);
        return AjaxResult.success(list);
    }

    // /**
    // * 获取团队详细信息 接口暂时作废
    // *
    // */
    // @PreAuthorize("@ss.hasPermi('password_manage:team:query')")
    // @Operation(summary = "获取团队信息", description = "查询团队信息及团队密钥用于加解密")
    // @GetMapping(value = "/{id}")
    // public AjaxResult getInfo(
    // @Parameter(name = "团队id", description = "团队id", in = ParameterIn.PATH)
    // @PathVariable("id") Long id) {
    // return success(passwordManageTeamService.selectPasswordManageTeamById(id));
    // }

    /**
     * 新增团队，更新创建人为管理员
     */
    @PreAuthorize("@ss.hasPermi('password_manage:team:add')")
    @Log(title = "团队密码管理-新增团队", businessType = BusinessType.INSERT)
    @Operation(summary = "新增团队", description = "新增团队，同时更新团队成员表")
    @PostMapping
    public AjaxResult add(@RequestBody PasswordManageTeamVo vo) {
        return success(passwordManageTeamService.insertPasswordManageTeam(vo));

    }

    /**
     * 团队密码管理-修改团队信息
     */
    @PreAuthorize("@ss.hasPermi('password_manage:team:edit')")
    @Log(title = "团队密码管理-团队密码", businessType = BusinessType.UPDATE)
    @Operation(summary = "修改团队信息")
    @PutMapping
    public AjaxResult edit(@RequestBody PasswordManageTeam passwordManageTeam) {
        return success(passwordManageTeamService.updatePasswordManageTeam(passwordManageTeam));
    }

    /**
     * 团队密码管理-删除团队,连携删除团队成员
     */
    @PreAuthorize("@ss.hasPermi('password_manage:team:remove')")
    @Log(title = "团队密码管理-团队密码", businessType = BusinessType.DELETE)
    @Operation(summary = "删除团队", description = "删除团队，同时删除团队成员")
    @DeleteMapping("/{id}")
    public AjaxResult remove(
            @Parameter(name = "团队id", description = "团队id", in = ParameterIn.PATH) @PathVariable Long teamId) {
        Long userId = getLoginUser().getUserId();
        PasswordManageUser user = passwordManageUserService.selectPasswordManageUserByUserId(userId);
        Integer role = passwordManageTeamRoleService.selectTeamRoleOfMember(teamId, user.getId());
        if (role == null || 0 != role) {
            return AjaxResult.error("非团队管理员，无凭据管理权限。");
        }
        return success(passwordManageTeamService.deletePasswordManageTeamById(teamId));
    }
}
