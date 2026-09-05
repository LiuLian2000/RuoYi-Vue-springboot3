package com.ruoyi.password_manage.controller;

import java.util.List;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ruoyi.common.utils.bean.BeanUtils;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.password_manage.domain.PasswordManageTeam;
import com.ruoyi.password_manage.domain.PasswordManageTeamCredential;
import com.ruoyi.password_manage.domain.vo.PasswordManageTeamCredentialVo;
import com.ruoyi.password_manage.service.IPasswordManageTeamCredentialService;
import com.ruoyi.password_manage.service.IPasswordManageTeamRoleService;
import com.ruoyi.password_manage.service.IPasswordManageTeamService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;

import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 团队密码凭据增删改查Controller
 * 
 * @author DiZhicong
 * @date 2026-09-02
 */
@RestController
@RequestMapping("/password_manage/credential")
public class PasswordManageTeamCredentialController extends BaseController {

    @Autowired
    private IPasswordManageTeamCredentialService passwordManageTeamCredentialService;

    @Autowired
    private IPasswordManageTeamRoleService passwordManageTeamRoleService;

    @Autowired
    private IPasswordManageTeamService passwordManageTeamService;

    /***
     * 查询团队密码凭据列表
     * 
     * @param teamId password_manage_team的id
     * @return
     */
    @PreAuthorize("@ss.hasPermi('password_manage:credential:list')")
    @GetMapping("/list")
    public TableDataInfo list(@Parameter(name = "团队身份密码") @RequestParam String authPassword,
            @Parameter(name = "团队id") @RequestParam Long teamId,
            @Parameter(name = "操作人的password_manage_user id") @RequestParam Long userId) {
        PasswordManageTeam team = passwordManageTeamService.selectPasswordManageTeamById(teamId);
        Integer role = passwordManageTeamRoleService.selectTeamRoleOfMember(teamId, userId);
        if (role == null) {
            TableDataInfo tableDataInfo = new TableDataInfo();
            tableDataInfo.setCode(500);
            tableDataInfo.setMsg("非团队成员，无凭据查看权限。");
            return tableDataInfo;
        }
        // 校验团队密码
        if (!team.getTeamPassword().equals(authPassword)) {
            TableDataInfo tableDataInfo = new TableDataInfo();
            tableDataInfo.setCode(500);
            tableDataInfo.setMsg("用户密码登陆错误");
            return tableDataInfo;
        }
        startPage();
        List<PasswordManageTeamCredential> list = passwordManageTeamCredentialService
                .selectPasswordManageTeamCredentialList(teamId);
        return getDataTable(list);
    }

    /***
     * 查询团队密码凭据
     * 
     * @param id password_manage_credential表的id
     * @return
     */
    @PreAuthorize("@ss.hasPermi('password_manage:credential:query')")
    @Operation(summary = "模糊查询团队密码", description = "团队id必填，平台选填")
    @GetMapping
    public AjaxResult getInfo(@Parameter(name = "团队id", required = true) @RequestParam Long teamId,
            @Parameter(name = "操作人的password_manage_user id", required = true) @RequestParam Long userId,
            @Parameter(name = "团队身份密码", required = true) @RequestParam String authPassword,
            @Parameter(name = "查看团队密码平台", required = false) @RequestParam String platName) {
        Integer role = passwordManageTeamRoleService.selectTeamRoleOfMember(teamId, userId);
        if (role == null) {
            return AjaxResult.error("非团队成员，无凭据查看权限。");
        }
        // 校验团队密码
        PasswordManageTeam team = passwordManageTeamService.selectPasswordManageTeamById(teamId);
        if (!team.getTeamPassword().equals(authPassword)) {
            return AjaxResult.error("团队密码验证错误。");
        }
        return success(
                passwordManageTeamCredentialService.selectPasswordManageTeamCredential(teamId, platName));
    }

    /**
     * 新增团队密码凭据
     */
    @PreAuthorize("@ss.hasPermi('password_manage:credential:add')")
    @Log(title = "团队密码凭据增删改查", businessType = BusinessType.INSERT)
    @Operation(summary = "新增团队密码凭据", description = "仅团队管理员可操作")
    @PostMapping
    public AjaxResult add(@RequestBody PasswordManageTeamCredentialVo vo) {
        Long operatedUserId = vo.getOperatedUserId();
        Long operatedTeamId = vo.getOperatedTeamId();
        Integer role = passwordManageTeamRoleService.selectTeamRoleOfMember(operatedTeamId, operatedUserId);
        if (role == null || 0 != role) {
            return AjaxResult.error("非团队管理员，无凭据管理权限。");
        }
        PasswordManageTeamCredential passwordManageTeamCredential = new PasswordManageTeamCredential();
        BeanUtils.copyProperties(vo, passwordManageTeamCredential);
        return toAjax(
                passwordManageTeamCredentialService.insertPasswordManageTeamCredential(passwordManageTeamCredential));
    }

    /**
     * 修改团队密码凭据
     */
    @PreAuthorize("@ss.hasPermi('password_manage:credential:edit')")
    @Log(title = "团队密码凭据增删改查", businessType = BusinessType.UPDATE)
    @Operation(summary = "修改团队密码凭据", description = "仅团队管理员可操作")
    @PutMapping
    public AjaxResult edit(@RequestBody PasswordManageTeamCredentialVo vo) {
        Long operatedUserId = vo.getOperatedUserId();
        Long operatedTeamId = vo.getOperatedTeamId();
        Integer role = passwordManageTeamRoleService.selectTeamRoleOfMember(operatedTeamId, operatedUserId);
        if (role == null || 0 != role) {
            return AjaxResult.error("非团队管理员，无凭据管理权限。");
        }
        PasswordManageTeamCredential passwordManageTeamCredential = new PasswordManageTeamCredential();
        BeanUtils.copyProperties(vo, passwordManageTeamCredential);
        return toAjax(
                passwordManageTeamCredentialService.updatePasswordManageTeamCredential(passwordManageTeamCredential));
    }

    /***
     * 删除团队密码凭据
     * 
     * @param id password_manage_credential表的id
     * @return
     */
    @PreAuthorize("@ss.hasPermi('password_manage:credential:remove')")
    @Operation(summary = "删除团队密码凭据", description = "仅团队管理员可操作")
    @Log(title = "团队密码凭据增删改查", businessType = BusinessType.DELETE)
    @DeleteMapping()
    public AjaxResult remove(@RequestBody PasswordManageTeamCredentialVo vo) {
        Long operatedUserId = vo.getOperatedUserId();
        Long operatedTeamId = vo.getOperatedTeamId();
        Integer role = passwordManageTeamRoleService.selectTeamRoleOfMember(operatedTeamId, operatedUserId);
        if (role == null || 0 != role) {
            return AjaxResult.error("非团队管理员，无凭据管理权限。");
        }
        return toAjax(passwordManageTeamCredentialService.deletePasswordManageTeamCredentialById(vo.getId()));
    }

}
