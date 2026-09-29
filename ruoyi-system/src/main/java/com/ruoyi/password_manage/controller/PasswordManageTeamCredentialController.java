package com.ruoyi.password_manage.controller;

import java.util.List;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.password_manage.domain.PasswordManageTeamCredential;
import com.ruoyi.password_manage.domain.PasswordManageTeamRole;
import com.ruoyi.password_manage.domain.vo.PasswordManageTeamCredentialVo;
import com.ruoyi.password_manage.domain.vo.TeamCredentialVo;
import com.ruoyi.password_manage.service.IPasswordManageTeamCredentialService;
import com.ruoyi.password_manage.service.IPasswordManageTeamRoleService;
import com.ruoyi.password_manage.service.PasswordManageUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;

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
        private PasswordManageUserService passwordManageUserService;

        /***
         * 查询团队密码凭据列表
         * 
         * @param teamId 团队id
         * @return 当前登陆用户所持有的团队金库密文及团队凭据密文
         */
        // TODO 这个方法中的错误码应该后续进行统一管理
        // @PreAuthorize("@ss.hasPermi('password_manage:credential:list')")
        @GetMapping("/list")
        public AjaxResult list(@Parameter(name = "团队id") @RequestParam Long teamId) {
                Long passwordManageUserId = passwordManageUserService
                                .selectPasswordManageUserByUserId(getLoginUser().getUserId()).getId();
                PasswordManageTeamRole role = passwordManageTeamRoleService
                                .selectPasswordManageTeamRoleByPasswordManageUserId(teamId, passwordManageUserId);
                if (role == null) {
                        throw new ServiceException("非团队成员，无团队凭据查看权限");
                }
                startPage();
                TeamCredentialVo vo = new TeamCredentialVo();
                List<PasswordManageTeamCredential> list = passwordManageTeamCredentialService
                                .selectPasswordManageTeamCredentialList(teamId);
                vo.setTeamValutKeyEncryptedCipher(role.getTeamValutKeyEncryptedCipher());
                vo.setTeamCredentials(list);
                return success(vo);
        }

        /**
         * 模糊查询团队密码凭据
         * 
         * @param teamId   团队id
         * @param platName 密码归属平台名称
         * @return
         */
        // @PreAuthorize("@ss.hasPermi('password_manage:credential:query')")
        @Operation(summary = "模糊查询团队密码", description = "团队id必填，平台选填")
        @GetMapping("/fuzzyQuery")
        public AjaxResult getInfo(@Parameter(name = "团队id", required = true) @RequestParam Long teamId,
                        @Parameter(name = "查看团队密码平台", required = false) @RequestParam String platName) {
                System.out.println("------>teamId:" + teamId + "------>platName:" + platName);
                Long passwordManageUserId = passwordManageUserService
                                .selectPasswordManageUserByUserId(getLoginUser().getUserId()).getId();
                PasswordManageTeamRole role = passwordManageTeamRoleService
                                .selectPasswordManageTeamRoleByPasswordManageUserId(teamId, passwordManageUserId);
                if (role == null) {
                        throw new ServiceException("非团队成员，无凭据查看权限。");
                }
                startPage();
                TeamCredentialVo vo = new TeamCredentialVo();
                List<PasswordManageTeamCredential> list = passwordManageTeamCredentialService
                                .selectPasswordManageTeamCredentialFuzzyList(teamId, platName);
                vo.setTeamValutKeyEncryptedCipher(role.getTeamValutKeyEncryptedCipher());
                vo.setTeamCredentials(list);
                return success(vo);
        }

        /**
         * 新增团队密码凭据
         */
        // @PreAuthorize("@ss.hasPermi('password_manage:credential:add')")
        @Log(title = "团队密码凭据增删改查", businessType = BusinessType.INSERT)
        @Operation(summary = "新增团队密码凭据", description = "仅团队管理员可操作")
        @PostMapping
        public AjaxResult add(@RequestBody PasswordManageTeamCredentialVo vo) {
                Long passwordManageUserId = passwordManageUserService
                                .selectPasswordManageUserByUserId(getLoginUser().getUserId()).getId();
                PasswordManageTeamRole role = passwordManageTeamRoleService
                                .selectPasswordManageTeamRoleByPasswordManageUserId(vo.getTeamId(),
                                                passwordManageUserId);
                if (role == null || 0 != role.getTeamRole()) {
                        throw new ServiceException("非团队管理员，无凭据管理权限。");
                }
                PasswordManageTeamCredential passwordManageTeamCredential = new PasswordManageTeamCredential();
                BeanUtils.copyProperties(vo, passwordManageTeamCredential);
                return success(
                                passwordManageTeamCredentialService
                                                .insertPasswordManageTeamCredential(passwordManageTeamCredential));
        }

        /**
         * 修改团队密码凭据
         */
        // @PreAuthorize("@ss.hasPermi('password_manage:credential:edit')")
        @Log(title = "团队密码凭据增删改查", businessType = BusinessType.UPDATE)
        @Operation(summary = "修改团队密码凭据", description = "仅团队管理员可操作")
        @PostMapping("/edit")
        public AjaxResult edit(@RequestBody PasswordManageTeamCredentialVo vo) {
                Long passwordManageUserId = passwordManageUserService
                                .selectPasswordManageUserByUserId(getLoginUser().getUserId()).getId();
                PasswordManageTeamRole role = passwordManageTeamRoleService
                                .selectPasswordManageTeamRoleByPasswordManageUserId(vo.getTeamId(),
                                                passwordManageUserId);
                if (role == null || 0 != role.getTeamRole()) {
                        throw new ServiceException("非团队管理员，无凭据管理权限。");
                }
                PasswordManageTeamCredential passwordManageTeamCredential = new PasswordManageTeamCredential();
                BeanUtils.copyProperties(vo, passwordManageTeamCredential);
                return success(
                                passwordManageTeamCredentialService
                                                .updatePasswordManageTeamCredential(passwordManageTeamCredential));
        }

        /***
         * 删除团队密码凭据
         * 
         * @param id     被删除凭据的id
         * @param teamId 被删除凭据所属团队的id
         * @return
         */
        // @PreAuthorize("@ss.hasPermi('password_manage:credential:remove')")
        @Operation(summary = "删除团队密码凭据", description = "仅团队管理员可操作")
        @Log(title = "团队密码凭据增删改查", businessType = BusinessType.DELETE)
        @DeleteMapping()
        public AjaxResult remove(@RequestParam Long id, @RequestParam Long teamId) {
                Long passwordManageUserId = passwordManageUserService
                                .selectPasswordManageUserByUserId(getLoginUser().getUserId()).getId();
                PasswordManageTeamRole role = passwordManageTeamRoleService
                                .selectPasswordManageTeamRoleByPasswordManageUserId(teamId, passwordManageUserId);
                if (role == null || 0 != role.getTeamRole()) {
                        throw new ServiceException("非团队管理员，无凭据管理权限。");
                }
                return success(passwordManageTeamCredentialService.deletePasswordManageTeamCredentialById(id));
        }

}
