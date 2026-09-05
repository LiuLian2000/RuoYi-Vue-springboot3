package com.ruoyi.password_manage.controller;

import java.util.List;
import jakarta.servlet.http.HttpServletResponse;
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
import com.ruoyi.password_manage.domain.PasswordManageTeamRole;
import com.ruoyi.password_manage.service.IPasswordManageTeamRoleService;
import com.ruoyi.password_manage.service.IPasswordManageTeamService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * 团队密码管理-团队密码Controller
 * 
 * @author DiZhicong
 * @date 2026-09-01
 */
@RestController
@RequestMapping("/password_manage/team")
@Tag(name = "团队信息及teamKey管理")
public class PasswordManageTeamController extends BaseController {
    @Autowired
    private IPasswordManageTeamService passwordManageTeamService;

    @Autowired
    private IPasswordManageTeamRoleService passwordManageTeamRoleService;

    /**
     * 查询管理团队列表
     * id 用户在password_manage_user中的id主键
     */
    @PreAuthorize("@ss.hasPermi('password_manage:team:list')")
    @Operation(summary = "查询管理团队列表", description = "团队管理员依赖此接口查询个人管理团队列表")
    @GetMapping("/ManageTeamlist/{id}")
    public AjaxResult getManageTeamlist(
            @Parameter(name = "用户id", description = "团队管理员在password_manage_user中的id", in = ParameterIn.PATH) @PathVariable("id") Long id) {
        startPage();
        List<PasswordManageTeam> list = passwordManageTeamService.selectPasswordManageTeamList(id);
        return AjaxResult.success(list);
    }

    /**
     * 获取团队详细信息
     * 
     */
    @PreAuthorize("@ss.hasPermi('password_manage:team:query')")
    @Operation(summary = "获取团队信息", description = "查询团队信息及团队密钥用于加解密")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(
            @Parameter(name = "团队id", description = "团队id", in = ParameterIn.PATH) @PathVariable("id") Long id) {
        return success(passwordManageTeamService.selectPasswordManageTeamById(id));
    }

    /**
     * 新增团队密码管理-新增团队，同时更新团队成员表
     */
    @PreAuthorize("@ss.hasPermi('password_manage:team:add')")
    @Log(title = "团队密码管理-新增团队", businessType = BusinessType.INSERT)
    @Operation(summary = "新增团队", description = "新增团队，同时更新团队成员表")
    @PostMapping
    public AjaxResult add(
            @Parameter(name = "团队", description = "passowrdMangeTeam对应实体", in = ParameterIn.DEFAULT) @RequestBody PasswordManageTeam passwordManageTeam) {
        int res = passwordManageTeamService.insertPasswordManageTeam(passwordManageTeam);
        PasswordManageTeamRole admin = new PasswordManageTeamRole();
        admin.setTeamId(passwordManageTeam.getId());
        admin.setTeamName(passwordManageTeam.getTeamName());
        admin.setUserId(passwordManageTeam.getCreateUserId());
        admin.setUserName(passwordManageTeam.getUserName());
        admin.setTeamRole(0);
        passwordManageTeamRoleService.insertPasswordManageTeamRole(admin);
        return toAjax(res);
    }

    /**
     * 团队密码管理-修改团队信息
     */
    @PreAuthorize("@ss.hasPermi('password_manage:team:edit')")
    @Log(title = "团队密码管理-团队密码", businessType = BusinessType.UPDATE)
    @Operation(summary = "修改团队信息")
    @PutMapping
    public AjaxResult edit(
            @Parameter(name = "团队", description = "passowrdMangeTeam对应实体", in = ParameterIn.DEFAULT) @RequestBody PasswordManageTeam passwordManageTeam) {
        return toAjax(passwordManageTeamService.updatePasswordManageTeam(passwordManageTeam));
    }

    /**
     * 团队密码管理-删除团队,连携删除团队成员
     */
    @PreAuthorize("@ss.hasPermi('password_manage:team:remove')")
    @Log(title = "团队密码管理-团队密码", businessType = BusinessType.DELETE)
    @Operation(summary = "删除团队", description = "删除团队，同时删除团队成员（此操作为物理删除）")
    @DeleteMapping("/{id}")
    public AjaxResult remove(
            @Parameter(name = "团队id", description = "团队id", in = ParameterIn.PATH) @PathVariable Long id) {
        return toAjax(passwordManageTeamService.deletePasswordManageTeamById(id));
    }
}
