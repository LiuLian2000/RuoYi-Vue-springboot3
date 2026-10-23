package com.ruoyi.password_manage.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.utils.bean.BeanUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.password_manage.common.constants.ExceptionMessages;
import com.ruoyi.password_manage.domain.PasswordManageTeamCredential;
import com.ruoyi.password_manage.domain.PasswordManageTeamRole;
import com.ruoyi.password_manage.domain.vo.PasswordManageTeamCredentialVo;
import com.ruoyi.password_manage.domain.vo.TeamCredentialVo;
import com.ruoyi.password_manage.service.IPasswordManageTeamCredentialService;
import com.ruoyi.password_manage.service.IPasswordManageTeamRoleService;
import com.ruoyi.password_manage.utils.TeamRoleVerifyUtils;
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
        private TeamRoleVerifyUtils teamRoleVerifyUtils;

        private Logger log = LoggerFactory.getLogger(PasswordManageTeamController.class);

        /**
         * 凭据管理权限：
         *  - 有分享人(sharerId)的凭据：仅分享人可修改/删除（满足“只有分享人才能修改，其他人只能查看”的需求）；
         *  - 历史数据(sharerId 为 null)：沿用原规则，仅团队管理员可修改/删除。
         */
        private void assertCanManageTeamCredential(Long credId, Long teamId) {
                PasswordManageTeamCredential existing = passwordManageTeamCredentialService
                                .selectPasswordManageTeamCredentialById(credId);
                if (existing == null) {
                        throw new ServiceException(ExceptionMessages.NORMAL);
                }
                if (existing.getSharerId() == null) {
                        teamRoleVerifyUtils.teamManagerVerify(teamId);
                        return;
                }
                Long loginUserId = SecurityUtils.getLoginUser().getUserId();
                if (!loginUserId.equals(existing.getSharerId())) {
                        throw new ServiceException("该凭据由分享人创建，只有分享人可修改或删除");
                }
        }

        /***
         * 查询团队密码凭据列表
         * 
         * @param teamId 团队id
         * @return 当前登陆用户所持有的团队金库密文及团队凭据密文
         */
        @PreAuthorize("@ss.hasPermi('system:passwordManage:operate')")
        @Log(title = "查询团队凭据列表", businessType = BusinessType.QUERY)
        @GetMapping("/list")
        public AjaxResult list(@Parameter(name = "团队id") @RequestParam Long teamId,
                        @Parameter(name = "平台名称，可选") @RequestParam(required = false) String platName) {
                try {
                        List<Object> tmpList = teamRoleVerifyUtils.teamMemberVerify(teamId);
                        Long passwordManageUserId = (Long) tmpList.get(0);
                        PasswordManageTeamRole role = passwordManageTeamRoleService
                                        .selectPasswordManageTeamRoleByPasswordManageUserId(teamId,
                                                        passwordManageUserId);
                        startPage();
                        TeamCredentialVo vo = new TeamCredentialVo();
                        List<PasswordManageTeamCredential> list = passwordManageTeamCredentialService
                                        .selectPasswordManageTeamCredentialFuzzyList(teamId, platName);
                        vo.setTeamValutKeyEncryptedCipher(role.getTeamValutKeyEncryptedCipher());
                        vo.setTeamCredentials(list);
                        return success(vo);
                } catch (ServiceException se) {
                        throw se;
                } catch (Exception e) {
                        log.info(e.getMessage(), e);
                        throw new ServiceException(ExceptionMessages.NORMAL);
                }

        }

        /**
         * 模糊查询团队密码凭据
         * 
         * @param teamId   团队id
         * @param platName 密码归属平台名称
         * @return
         */
        @PreAuthorize("@ss.hasPermi('system:passwordManage:operate')")
        @Log(title = "模糊查询团队凭据", businessType = BusinessType.QUERY)
        @GetMapping("/fuzzyQuery")
        public AjaxResult getInfo(@Parameter(name = "团队id", required = true) @RequestParam Long teamId,
                        @Parameter(name = "查看团队密码平台", required = false) @RequestParam(required = false) String platName) {
                try {
                        List<Object> tmpList = teamRoleVerifyUtils.teamMemberVerify(teamId);
                        PasswordManageTeamRole role = (PasswordManageTeamRole) tmpList.get(1);
                        startPage();
                        TeamCredentialVo vo = new TeamCredentialVo();
                        List<PasswordManageTeamCredential> list = passwordManageTeamCredentialService
                                        .selectPasswordManageTeamCredentialFuzzyList(teamId, platName);
                        vo.setTeamValutKeyEncryptedCipher(role.getTeamValutKeyEncryptedCipher());
                        vo.setTeamCredentials(list);
                        return success(vo);
                } catch (ServiceException se) {
                        throw se;
                } catch (Exception e) {
                        log.info(e.getMessage(), e);
                        throw new ServiceException(ExceptionMessages.NORMAL);
                }

        }

        /**
         * 新增团队密码凭据
         */
        @PreAuthorize("@ss.hasPermi('system:passwordManage:operate')")
        @Log(title = "新增团队凭据", businessType = BusinessType.INSERT)
        @PostMapping
        public AjaxResult add(@RequestBody PasswordManageTeamCredentialVo vo) {
                try {
                        teamRoleVerifyUtils.teamManagerVerify(vo.getTeamId());
                        PasswordManageTeamCredential passwordManageTeamCredential = new PasswordManageTeamCredential();
                        BeanUtils.copyProperties(vo, passwordManageTeamCredential);
                        return success(
                                        passwordManageTeamCredentialService
                                                        .insertPasswordManageTeamCredential(
                                                                        passwordManageTeamCredential));
                } catch (ServiceException se) {
                        throw se;
                } catch (Exception e) {
                        log.info(e.getMessage(), e);
                        throw new ServiceException(ExceptionMessages.NORMAL);
                }

        }

        /**
         * 修改团队密码凭据
         */
        @PreAuthorize("@ss.hasPermi('system:passwordManage:operate')")
        @Log(title = "修改团队凭据", businessType = BusinessType.UPDATE)
        @PostMapping("/edit")
        public AjaxResult edit(@RequestBody PasswordManageTeamCredentialVo vo) {
                try {
                        assertCanManageTeamCredential(vo.getId(), vo.getTeamId());
                        PasswordManageTeamCredential passwordManageTeamCredential = new PasswordManageTeamCredential();
                        BeanUtils.copyProperties(vo, passwordManageTeamCredential);
                        return success(
                                        passwordManageTeamCredentialService
                                                        .updatePasswordManageTeamCredential(
                                                                        passwordManageTeamCredential));
                } catch (ServiceException se) {
                        throw se;
                } catch (Exception e) {
                        log.info(e.getMessage(), e);
                        throw new ServiceException(ExceptionMessages.NORMAL);
                }

        }

        /***
         * 删除团队密码凭据
         * 
         * @param id     被删除凭据的id
         * @param teamId 被删除凭据所属团队的id
         * @return
         */
        @PreAuthorize("@ss.hasPermi('system:passwordManage:operate')")
        @Log(title = "删除团队凭据", businessType = BusinessType.DELETE)
        @DeleteMapping()
        public AjaxResult remove(@RequestParam Long id, @RequestParam Long teamId) {
                try {
                        assertCanManageTeamCredential(id, teamId);
                        return success(passwordManageTeamCredentialService.deletePasswordManageTeamCredentialById(id));
                } catch (ServiceException se) {
                        throw se;
                } catch (Exception e) {
                        log.info(e.getMessage(), e);
                        throw new ServiceException(ExceptionMessages.NORMAL);
                }

        }

}
