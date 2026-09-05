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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;

import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.password_manage.domain.PasswordManageUser;
import com.ruoyi.password_manage.service.PasswordManageUserService;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 用户密码管理主，绑定系统的sys_userController
 * 
 * @author ruoyi
 * @date 2026-08-26
 */
@RestController
@RequestMapping("/system/passwordManage/user")
public class PasswordManageUserController extends BaseController {
    @Autowired
    private PasswordManageUserService passwordManageUserService;

    /**
     * 查询用户密码管理主，绑定系统的sys_user列表
     */
    @PreAuthorize("@ss.hasPermi('system:user:list')")
    @GetMapping("/list")
    public TableDataInfo list(PasswordManageUser passwordManageUser) {
        startPage();
        List<PasswordManageUser> list = passwordManageUserService.selectPasswordManageUserList(passwordManageUser);
        return getDataTable(list);
    }

    /**
     * 导出用户密码管理主，绑定系统的sys_user列表
     */
    @PreAuthorize("@ss.hasPermi('system:user:export')")
    @Log(title = "用户密码管理主，绑定系统的sys_user", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, PasswordManageUser passwordManageUser) {
        List<PasswordManageUser> list = passwordManageUserService.selectPasswordManageUserList(passwordManageUser);
        ExcelUtil<PasswordManageUser> util = new ExcelUtil<PasswordManageUser>(PasswordManageUser.class);
        util.exportExcel(response, list, "用户密码管理主，绑定系统的sys_user数据");
    }

    /**
     * 获取用户密码管理主，绑定系统的sys_user详细信息
     * 
     */
    @PreAuthorize("@ss.hasPermi('system:user:query')")
    @GetMapping(value = "/getInfo/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return success(passwordManageUserService.selectPasswordManageUserById(id));
    }

    @PreAuthorize("@ss.hasPermi('system:user:query')")
    @GetMapping(value = "/getInfoByUserId/{userId}")
    @Operation(summary = "获取用户valutKey", description = "获取系统用户加密所使用的salt、iv和valutKey")
    public AjaxResult getInfoByUserId(
            @Parameter(description = "用户在sys_user的主键id") @PathVariable("userId") Long userId) {
        return success(passwordManageUserService.selectPasswordManageUserByUserId(userId));
    }

    /**
     * 新增用户密码管理主，绑定系统的sys_user
     */
    @PreAuthorize("@ss.hasPermi('system:user:add')")
    @Log(title = "用户密码管理主，绑定系统的sys_user", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody PasswordManageUser passwordManageUser) {
        return toAjax(passwordManageUserService.insertPasswordManageUser(passwordManageUser));
    }

    /**
     * 修改用户密码管理主，绑定系统的sys_user
     */
    @PreAuthorize("@ss.hasPermi('system:user:edit')")
    @Log(title = "用户密码管理主，绑定系统的sys_user", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody PasswordManageUser passwordManageUser) {
        return toAjax(passwordManageUserService.updatePasswordManageUser(passwordManageUser));
    }

    /**
     * 删除用户密码管理主，绑定系统的sys_user
     */
    @PreAuthorize("@ss.hasPermi('system:user:remove')")
    @Log(title = "用户密码管理主，绑定系统的sys_user", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(passwordManageUserService.deletePasswordManageUserByIds(ids));
    }
}
