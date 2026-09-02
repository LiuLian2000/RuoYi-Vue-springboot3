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
import io.swagger.v3.oas.annotations.tags.Tag;

import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

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
    // /**
    // * 查询团队密码管理-团队密码列表
    // */
    // @PreAuthorize("@ss.hasPermi('password_manage:team:list')")
    // @GetMapping("/list")
    // public TableDataInfo list(PasswordManageTeam passwordManageTeam)
    // {
    // startPage();
    // List<PasswordManageTeam> list =
    // passwordManageTeamService.selectPasswordManageTeamList(passwordManageTeam);
    // return getDataTable(list);
    // }

    // /**
    // * 导出团队密码管理-团队密码列表
    // */
    // @PreAuthorize("@ss.hasPermi('password_manage:team:export')")
    // @Log(title = "团队密码管理-团队密码", businessType = BusinessType.EXPORT)
    // @PostMapping("/export")
    // public void export(HttpServletResponse response, PasswordManageTeam
    // passwordManageTeam)
    // {
    // List<PasswordManageTeam> list =
    // passwordManageTeamService.selectPasswordManageTeamList(passwordManageTeam);
    // ExcelUtil<PasswordManageTeam> util = new
    // ExcelUtil<PasswordManageTeam>(PasswordManageTeam.class);
    // util.exportExcel(response, list, "团队密码管理-团队密码数据");
    // }

    /**
     * 查询管理团队列表
     */
    @PreAuthorize("@ss.hasPermi('password_manage:team:list')")
    @Operation(summary = "查询管理团队列表", description = "查询管理团队列表")
    @GetMapping("/ManageTeamlist")
    public AjaxResult getManageTeamlist(@PathVariable("id") Long id) {
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
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return success(passwordManageTeamService.selectPasswordManageTeamById(id));
    }

    /**
     * 新增团队密码管理-新增团队，同时更新团队成员表
     */
    @PreAuthorize("@ss.hasPermi('password_manage:team:add')")
    @Log(title = "团队密码管理-新增团队", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody PasswordManageTeam passwordManageTeam) {
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
     * 团队密码管理-团队密码
     */
    @PreAuthorize("@ss.hasPermi('password_manage:team:edit')")
    @Log(title = "团队密码管理-团队密码", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody PasswordManageTeam passwordManageTeam) {
        return toAjax(passwordManageTeamService.updatePasswordManageTeam(passwordManageTeam));
    }

    // /**
    // * 删除团队密码管理-团队密码
    // */
    // @PreAuthorize("@ss.hasPermi('password_manage:team:remove')")
    // @Log(title = "团队密码管理-团队密码", businessType = BusinessType.DELETE)
    // @DeleteMapping("/{ids}")
    // public AjaxResult remove(@PathVariable Long[] ids) {
    // return toAjax(passwordManageTeamService.deletePasswordManageTeamByIds(ids));
    // }

    /**
     * 团队密码管理-删除团队,连携删除团队成员
     */
    @PreAuthorize("@ss.hasPermi('password_manage:team:remove')")
    @Log(title = "团队密码管理-团队密码", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long id) {
        return toAjax(passwordManageTeamService.deletePasswordManageTeamById(id));
    }
}
