package com.ruoyi.password_manage.controller;

import java.util.List;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.password_manage.common.constants.ExceptionMessages;
import com.ruoyi.password_manage.domain.PasswordManageUser;
import com.ruoyi.password_manage.domain.PasswordManageUserCredential;
import com.ruoyi.password_manage.service.IPasswordManageUserCredentialService;
import com.ruoyi.password_manage.service.PasswordManageUserService;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 用户凭据管理
 * 
 * @author ruoyi
 * @date 2026-08-26
 */
@RestController
@RequestMapping("/system/passwordManage/credential")
public class PasswordManageUserCredentialController extends BaseController {

    @Autowired
    private IPasswordManageUserCredentialService passwordManageUserCredentialService;

    @Autowired
    private PasswordManageUserService passwordManageUserService;

    private Logger log = LoggerFactory.getLogger(PasswordManageTeamController.class);

    /**
     * 分页查询用户密码管理凭据
     */
    @PreAuthorize("@ss.hasPermi('system:passwordManage:operate')")
    @Log(title = "分页查询用户凭据", businessType = BusinessType.QUERY)
    @GetMapping("/list")
    public TableDataInfo list(PasswordManageUserCredential passwordManageUserCredential) {
        try {
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
        } catch (ServiceException se) {
            throw se;
        } catch (Exception e) {
            log.info(e.getMessage(), e);
            throw new ServiceException(ExceptionMessages.NORMAL);
        }

    }

    /**
     * 导出用户密码管理凭据
     */
    @PreAuthorize("@ss.hasPermi('system:passwordManage:operate')")
    @Log(title = "导出用户凭据", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, PasswordManageUserCredential passwordManageUserCredential) {
        try {
            Long pmUserId = resolveCurrentPmUserId();
            if (pmUserId != null) {
                passwordManageUserCredential.setPasswordManageUserId(pmUserId);
            }
            List<PasswordManageUserCredential> list = passwordManageUserCredentialService
                    .selectPasswordManageUserCredentialList(passwordManageUserCredential);
            ExcelUtil<PasswordManageUserCredential> util = new ExcelUtil<PasswordManageUserCredential>(
                    PasswordManageUserCredential.class);
            util.exportExcel(response, list, "用户凭据");
        } catch (ServiceException se) {
            throw se;
        } catch (Exception e) {
            log.info(e.getMessage(), e);
            throw new ServiceException(ExceptionMessages.NORMAL);
        }

    }

    /**
     * 获取用户密码管理凭据
     */
    @PreAuthorize("@ss.hasPermi('system:passwordManage:operate')")
    @Log(title = "获取用户凭据", businessType = BusinessType.QUERY)
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        try {
            PasswordManageUserCredential credential = passwordManageUserCredentialService
                    .selectPasswordManageUserCredentialById(id);
            Long pmUserId = resolveCurrentPmUserId();
            // 越权防护：只能查看属于自己的凭据
            if (credential == null || pmUserId == null || !pmUserId.equals(credential.getPasswordManageUserId())) {
                throw new ServiceException(ExceptionMessages.USER_NO_PER_ACCESS);
            }
            return success(credential);
        } catch (ServiceException se) {
            throw se;
        } catch (Exception e) {
            log.info(e.getMessage(), e);
            throw new ServiceException(ExceptionMessages.NORMAL);
        }

    }

    /**
     * 新增用户密码管理凭据
     */
    @PreAuthorize("@ss.hasPermi('system:passwordManage:operate')")
    @Log(title = "新增用户凭据", businessType = BusinessType.INSERT)
    @PostMapping(value = "/add")
    public AjaxResult add(@RequestBody PasswordManageUserCredential passwordManageUserCredential) {
        try {
            // 外键 password_manage_user_id 指向 password_manage_user.id（主键），
            // 由后端按当前登录用户自动回填，前端无需也不应传该值（防越权改写他人凭据）。
            Long pmUserId = resolveCurrentPmUserId();
            if (pmUserId == null) {
                throw new ServiceException(ExceptionMessages.VAULT_UNLOCK);
            }
            passwordManageUserCredential.setPasswordManageUserId(pmUserId);
            return toAjax(
                    passwordManageUserCredentialService
                            .insertPasswordManageUserCredential(passwordManageUserCredential));
        } catch (ServiceException se) {
            throw se;
        } catch (Exception e) {
            log.info(e.getMessage(), e);
            throw new ServiceException(ExceptionMessages.NORMAL);
        }
    }

    /**
     * 修改用户密码管理凭据
     */
    @PreAuthorize("@ss.hasPermi('system:passwordManage:operate')")
    @Log(title = "修改用户凭据", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody PasswordManageUserCredential passwordManageUserCredential) {
        try {
            // 同 add：外键由后端按当前登录用户回填，确保只能修改自己的凭据。
            Long pmUserId = resolveCurrentPmUserId();
            if (pmUserId == null) {
                throw new ServiceException(ExceptionMessages.VAULT_UNLOCK);
            }
            passwordManageUserCredential.setPasswordManageUserId(pmUserId);
            return toAjax(
                    passwordManageUserCredentialService
                            .updatePasswordManageUserCredential(passwordManageUserCredential));
        } catch (ServiceException se) {
            throw se;
        } catch (Exception e) {
            log.info(e.getMessage(), e);
            throw new ServiceException(ExceptionMessages.NORMAL);
        }

    }

    /**
     * 批量删除用户密码管理凭据
     */
    @PreAuthorize("@ss.hasPermi('system:passwordManage:operate')")
    @Log(title = "批量删除用户凭据", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        try {
            Long pmUserId = resolveCurrentPmUserId();
            if (pmUserId == null) {
                throw new ServiceException(ExceptionMessages.VAULT_UNLOCK);
            }
            // 越权防护：仅允许删除自己的凭据
            for (Long id : ids) {
                PasswordManageUserCredential c = passwordManageUserCredentialService
                        .selectPasswordManageUserCredentialById(id);
                if (c == null || !pmUserId.equals(c.getPasswordManageUserId())) {
                    throw new ServiceException(ExceptionMessages.USER_NO_PER_ACCESS);
                }
            }
            return toAjax(passwordManageUserCredentialService.deletePasswordManageUserCredentialByIds(ids));
        } catch (ServiceException se) {
            throw se;
        } catch (Exception e) {
            log.info(e.getMessage(), e);
            throw new ServiceException(ExceptionMessages.NORMAL);
        }

    }

    /**
     * 删除用户密码管理凭据
     */
    @PreAuthorize("@ss.hasPermi('system:passwordManage:operate')")
    @Log(title = "删除用户凭据", businessType = BusinessType.DELETE)
    @DeleteMapping("/delete/{id}")
    public AjaxResult remove(@PathVariable Long id) {
        try {
            Long pmUserId = resolveCurrentPmUserId();
            if (pmUserId == null) {
                throw new ServiceException(ExceptionMessages.VAULT_UNLOCK);
            }
            PasswordManageUserCredential c = passwordManageUserCredentialService
                    .selectPasswordManageUserCredentialById(id);
            if (c == null || !pmUserId.equals(c.getPasswordManageUserId())) {
                throw new ServiceException(ExceptionMessages.USER_NO_PER_ACCESS);
            }
            return toAjax(passwordManageUserCredentialService.deletePasswordManageUserCredentialById(id));
        } catch (ServiceException se) {
            throw se;
        } catch (Exception e) {
            log.info(e.getMessage(), e);
            throw new ServiceException(ExceptionMessages.NORMAL);
        }

    }

    /**
     * 取当前登录用户对应的 password_manage_user.id（用于凭据按用户隔离）。
     * 未初始化金库时返回 null。
     */
    private Long resolveCurrentPmUserId() {
        PasswordManageUser pmUser = passwordManageUserService.selectPasswordManageUserByUserId(getUserId());
        return pmUser == null ? null : pmUser.getId();
    }
}
