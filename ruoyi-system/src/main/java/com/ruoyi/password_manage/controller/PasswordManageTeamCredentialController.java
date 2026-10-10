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

        /***
         * 查询团队密码凭据列表
         * 
         * @param teamId 团队id
         * @return 当前登陆用户所持有的团队金库密文及团队凭据密文
         */
        @PreAuthorize("@ss.hasPermi('system:passwordManage:operate')")
        @Log(title = "查询团队凭据列表", businessType = BusinessType.QUERY)
        @GetMapping("/list")
        public AjaxResult list(@Parameter(name = "团队id") @RequestParam Long teamId) {
                try {
                        List<Object> tmpList = teamRoleVerifyUtils.teamMemberVerify(teamId);
                        Long passwordManageUserId = (Long) tmpList.get(0);
                        PasswordManageTeamRole role = passwordManageTeamRoleService
                                        .selectPasswordManageTeamRoleByPasswordManageUserId(teamId,
                                                        passwordManageUserId);
                        startPage();
                        TeamCredentialVo vo = new TeamCredentialVo();
                        List<PasswordManageTeamCredential> list = passwordManageTeamCredentialService
                                        .selectPasswordManageTeamCredentialList(teamId);
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
                        @Parameter(name = "查看团队密码平台", required = false) @RequestParam String platName) {
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
                        teamRoleVerifyUtils.teamManagerVerify(vo.getTeamId());
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
                        teamRoleVerifyUtils.teamManagerVerify(teamId);
                        return success(passwordManageTeamCredentialService.deletePasswordManageTeamCredentialById(id));
                } catch (ServiceException se) {
                        throw se;
                } catch (Exception e) {
                        log.info(e.getMessage(), e);
                        throw new ServiceException(ExceptionMessages.NORMAL);
                }

        }

}
