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
import com.ruoyi.password_manage.domain.PasswordManageTeamRole;
import com.ruoyi.password_manage.service.IPasswordManageTeamRoleService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 用户在团队中的角色Controller
 * 
 * @author DiZhicong
 * @date 2026-09-02
 */
@RestController
@RequestMapping("/password_manage/role")
public class PasswordManageTeamRoleController extends BaseController
{
    @Autowired
    private IPasswordManageTeamRoleService passwordManageTeamRoleService;

    /**
     * 查询用户在团队中的角色列表
     */
    @PreAuthorize("@ss.hasPermi('password_manage:role:list')")
    @GetMapping("/list")
    public TableDataInfo list(PasswordManageTeamRole passwordManageTeamRole)
    {
        startPage();
        List<PasswordManageTeamRole> list = passwordManageTeamRoleService.selectPasswordManageTeamRoleList(passwordManageTeamRole);
        return getDataTable(list);
    }

    /**
     * 导出用户在团队中的角色列表
     */
    @PreAuthorize("@ss.hasPermi('password_manage:role:export')")
    @Log(title = "用户在团队中的角色", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, PasswordManageTeamRole passwordManageTeamRole)
    {
        List<PasswordManageTeamRole> list = passwordManageTeamRoleService.selectPasswordManageTeamRoleList(passwordManageTeamRole);
        ExcelUtil<PasswordManageTeamRole> util = new ExcelUtil<PasswordManageTeamRole>(PasswordManageTeamRole.class);
        util.exportExcel(response, list, "用户在团队中的角色数据");
    }

    /**
     * 获取用户在团队中的角色详细信息
     */
    @PreAuthorize("@ss.hasPermi('password_manage:role:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(passwordManageTeamRoleService.selectPasswordManageTeamRoleById(id));
    }

    /**
     * 新增用户在团队中的角色
     */
    @PreAuthorize("@ss.hasPermi('password_manage:role:add')")
    @Log(title = "用户在团队中的角色", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody PasswordManageTeamRole passwordManageTeamRole)
    {
        return toAjax(passwordManageTeamRoleService.insertPasswordManageTeamRole(passwordManageTeamRole));
    }

    /**
     * 修改用户在团队中的角色
     */
    @PreAuthorize("@ss.hasPermi('password_manage:role:edit')")
    @Log(title = "用户在团队中的角色", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody PasswordManageTeamRole passwordManageTeamRole)
    {
        return toAjax(passwordManageTeamRoleService.updatePasswordManageTeamRole(passwordManageTeamRole));
    }

    /**
     * 删除用户在团队中的角色
     */
    @PreAuthorize("@ss.hasPermi('password_manage:role:remove')")
    @Log(title = "用户在团队中的角色", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(passwordManageTeamRoleService.deletePasswordManageTeamRoleByIds(ids));
    }
}
