package com.ruoyi.web.controller.passwordManage;

import java.util.List;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Description;
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
import com.ruoyi.system.domain.PasswordManageUser;
import com.ruoyi.system.domain.PasswordManageUserCredential;
import com.ruoyi.system.service.IPasswordManageUserCredentialService;
import com.ruoyi.system.service.PasswordManageUserService;

import io.swagger.v3.oas.annotations.Operation;

import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 用户密码管理子，绑定password_manage_userController
 * 
 * @author ruoyi
 * @date 2026-08-26
 */
@RestController
@RequestMapping("system/passwordManage/credential")
public class PasswordManageUserCredentialController extends BaseController {
    @Autowired
    private IPasswordManageUserCredentialService passwordManageUserCredentialService;
    @Autowired
    private PasswordManageUserService passwordManageUserService;

    /**
     * 分页查询用户密码管理凭据
     */
    @PreAuthorize("@ss.hasPermi('system:credential:list')")
    @Operation(summary = " 分页查询用户密码管理凭据", description = "get请求,url拼接pageNum和pageSize")
    @GetMapping("/list")
    public TableDataInfo list(PasswordManageUserCredential passwordManageUserCredential) {
        // 强制按当前登录用户过滤，避免越权看到他人凭据
        Long pmUserId = resolveCurrentPmUserId();
        if (pmUserId == null) {
            return getDataTable(new java.util.ArrayList<>());
        }
        passwordManageUserCredential.setPasswordManageUserId(pmUserId);
        startPage();
        List<PasswordManageUserCredential> list = passwordManageUserCredentialService
                .selectPasswordManageUserCredentialList(passwordManageUserCredential);
        return getDataTable(list);
    }

    /**
     * 导出用户密码管理凭据
     */
    @PreAuthorize("@ss.hasPermi('system:credential:export')")
    @Log(title = "用户密码管理子，绑定password_manage_user", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, PasswordManageUserCredential passwordManageUserCredential) {
        Long pmUserId = resolveCurrentPmUserId();
        if (pmUserId != null) {
            passwordManageUserCredential.setPasswordManageUserId(pmUserId);
        }
        List<PasswordManageUserCredential> list = passwordManageUserCredentialService
                .selectPasswordManageUserCredentialList(passwordManageUserCredential);
        ExcelUtil<PasswordManageUserCredential> util = new ExcelUtil<PasswordManageUserCredential>(
                PasswordManageUserCredential.class);
        util.exportExcel(response, list, "用户密码管理子，绑定password_manage_user数据");
    }

    /**
     * 取当前登录用户对应的 password_manage_user.id（用于凭据按用户隔离）。
     * 未初始化金库时返回 null。
     */
    private Long resolveCurrentPmUserId() {
        PasswordManageUser pmUser = passwordManageUserService.selectPasswordManageUserByUserId(getUserId());
        return pmUser == null ? null : pmUser.getId();
    }


    /**
     * 获取用户密码管理凭据
     */
    @PreAuthorize("@ss.hasPermi('system:credential:query')")
    @Operation(summary = "获取用户密码管理凭据", description = "根据id获取单条用户密码管理凭据")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        PasswordManageUserCredential credential = passwordManageUserCredentialService
                .selectPasswordManageUserCredentialById(id);
        Long pmUserId = resolveCurrentPmUserId();
        // 越权防护：只能查看属于自己的凭据
        if (credential == null || pmUserId == null || !pmUserId.equals(credential.getPasswordManageUserId())) {
            return error("无权访问该凭据");
        }
        return success(credential);
    }

    /**
     * 新增用户密码管理凭据
     */
    @PreAuthorize("@ss.hasPermi('system:credential:add')")
    @Log(title = "用户密码管理子，绑定password_manage_user", businessType = BusinessType.INSERT)
    @Operation(summary = "新增用户密码管理凭据", description = "passwordManageUserId对应内容为PasswordManageUser中的id主键")
    @PostMapping(value = "/add")
    public AjaxResult add(@RequestBody PasswordManageUserCredential passwordManageUserCredential) {
        // 外键 password_manage_user_id 指向 password_manage_user.id（主键），
        // 由后端按当前登录用户自动回填，前端无需也不应传该值（防越权改写他人凭据）。
        Long pmUserId = resolveCurrentPmUserId();
        if (pmUserId == null) {
            return error("当前用户尚未初始化金库，无法新增凭据");
        }
        passwordManageUserCredential.setPasswordManageUserId(pmUserId);
        return toAjax(
                passwordManageUserCredentialService.insertPasswordManageUserCredential(passwordManageUserCredential));
    }

    /**
     * 修改用户密码管理凭据
     */
    @PreAuthorize("@ss.hasPermi('system:credential:edit')")
    @Log(title = "用户密码管理子，绑定password_manage_user", businessType = BusinessType.UPDATE)
    @Operation(summary = "修改用户密码管理凭据", description = "passwordManageUserId对应内容为PasswordManageUser中的id主键")
    @PutMapping
    public AjaxResult edit(@RequestBody PasswordManageUserCredential passwordManageUserCredential) {
        // 同 add：外键由后端按当前登录用户回填，确保只能修改自己的凭据。
        Long pmUserId = resolveCurrentPmUserId();
        if (pmUserId == null) {
            return error("当前用户尚未初始化金库，无法修改凭据");
        }
        passwordManageUserCredential.setPasswordManageUserId(pmUserId);
        return toAjax(
                passwordManageUserCredentialService.updatePasswordManageUserCredential(passwordManageUserCredential));
    }

    /**
     * 批量删除用户密码管理凭据
     */
    @PreAuthorize("@ss.hasPermi('system:credential:remove')")
    @Log(title = "用户密码管理子，绑定password_manage_user", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        Long pmUserId = resolveCurrentPmUserId();
        if (pmUserId == null) {
            return error("当前用户尚未初始化金库");
        }
        // 越权防护：仅允许删除自己的凭据
        for (Long id : ids) {
            PasswordManageUserCredential c = passwordManageUserCredentialService
                    .selectPasswordManageUserCredentialById(id);
            if (c == null || !pmUserId.equals(c.getPasswordManageUserId())) {
                return error("无权删除该凭据");
            }
        }
        return toAjax(passwordManageUserCredentialService.deletePasswordManageUserCredentialByIds(ids));
    }

    /**
     * 删除用户密码管理凭据
     */
    @PreAuthorize("@ss.hasPermi('system:credential:remove')")
    @Log(title = "用户密码管理子，绑定password_manage_user", businessType = BusinessType.DELETE)
    @Operation(summary = "删除用户密码管理凭据")
    @DeleteMapping("/delete/{id}")
    public AjaxResult remove(@PathVariable Long id) {
        Long pmUserId = resolveCurrentPmUserId();
        if (pmUserId == null) {
            return error("当前用户尚未初始化金库");
        }
        PasswordManageUserCredential c = passwordManageUserCredentialService
                .selectPasswordManageUserCredentialById(id);
        if (c == null || !pmUserId.equals(c.getPasswordManageUserId())) {
            return error("无权删除该凭据");
        }
        return toAjax(passwordManageUserCredentialService.deletePasswordManageUserCredentialById(id));
    }
}
