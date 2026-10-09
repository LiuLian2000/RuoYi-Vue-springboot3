package com.ruoyi.password_manage.controller;

import java.util.List;

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
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.password_manage.common.constants.ExceptionMessages;
import com.ruoyi.password_manage.domain.PasswordManageUser;
import com.ruoyi.password_manage.domain.dto.EditPasswordManageTeamDto;
import com.ruoyi.password_manage.domain.vo.MyManagementTeamVo;
import com.ruoyi.password_manage.domain.vo.MyTeamVo;
import com.ruoyi.password_manage.domain.vo.PasswordManageTeamVo;
import com.ruoyi.password_manage.service.IPasswordManageTeamRoleService;
import com.ruoyi.password_manage.service.IPasswordManageTeamService;
import com.ruoyi.password_manage.service.PasswordManageUserService;
import com.ruoyi.password_manage.utils.TeamRoleVerifyUtils;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;

/**
 * 团队密码管理-团队密码Controller
 * 
 * @author DiZhicong
 * @date 2026-09-01
 */
@RestController
@RequestMapping("/password_manage/team")
public class PasswordManageTeamController extends BaseController {

    @Autowired
    private IPasswordManageTeamService passwordManageTeamService;

    @Autowired
    private PasswordManageUserService passwordManageUserService;

    @Autowired
    private IPasswordManageTeamRoleService passwordManageTeamRoleService;

    @Autowired
    private TeamRoleVerifyUtils teamRoleVerifyUtils;

    private Logger log = LoggerFactory.getLogger(PasswordManageTeamController.class);

    /**
     * 查询管理团队列表
     */
    // @PreAuthorize("@ss.hasPermi('password_manage:team:list')")
    @Log(title = "查询管理团队列表", businessType = BusinessType.QUERY)
    @GetMapping("/ManageTeamlist")
    public AjaxResult getManageTeamlist() {
        startPage();
        Long sysusrId = getLoginUser().getUserId();
        PasswordManageUser passowrdManageUser = passwordManageUserService.selectPasswordManageUserByUserId(sysusrId);
        if (passowrdManageUser == null) {
            throw new ServiceException(ExceptionMessages.VAULT_UNLOCK);
        }
        try {
            Long passwordManageUserId = passowrdManageUser.getId();
            List<MyManagementTeamVo> list = passwordManageTeamService
                    .selectManageTeamList(passwordManageUserId);
            return success(list);
        } catch (ServiceException se) {
            throw se;
        } catch (Exception e) {
            log.info(e.getMessage(), e);
            throw new ServiceException(ExceptionMessages.NORMAL);
        }

    }

    /**
     * 查询用户所在的团队列表
     */
    // @PreAuthorize("@ss.hasPermi('password_manage:role:team_list')")
    @Log(title = "查询所在团队列表", businessType = BusinessType.QUERY)
    @GetMapping("/myTeam")
    public TableDataInfo getTeamList() {
        try {
            startPage();
            List<MyTeamVo> list = passwordManageTeamRoleService
                    .selectPasswordManageTeamList(getLoginUser().getUserId());
            return getDataTable(list);
        } catch (ServiceException se) {
            throw se;
        } catch (Exception e) {
            log.info(e.getMessage(), e);
            throw new ServiceException(ExceptionMessages.NORMAL);
        }

    }

    /**
     * 新增团队，更新创建人为超级管理员
     * 入参只需要team_name,reamrk,teamValutKeyEncryptedCipher
     */
    // @PreAuthorize("@ss.hasPermi('password_manage:team:add')")
    @Log(title = "新增团队", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody PasswordManageTeamVo vo) {
        try {
            Long teamId = passwordManageTeamService.insertPasswordManageTeam(vo);
            return success(teamId);
        } catch (ServiceException se) {
            throw se;
        } catch (Exception e) {
            log.info(e.getMessage(), e);
            throw new ServiceException(ExceptionMessages.NORMAL);
        }

    }

    /**
     * 团队密码管理-修改团队信息
     */
    // @PreAuthorize("@ss.hasPermi('password_manage:team:edit')")
    @Log(title = "修改团队", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody EditPasswordManageTeamDto dto) {
        try {
            Long teamId = dto.getId();
            teamRoleVerifyUtils.teamManagerVerify(teamId);
            passwordManageTeamService.updatePasswordManageTeam(dto);
            return success();
        } catch (ServiceException se) {
            throw se;
        } catch (Exception e) {
            log.info(e.getMessage(), e);
            throw new ServiceException(ExceptionMessages.NORMAL);
        }

    }

    /**
     * 解散团队
     */
    // @PreAuthorize("@ss.hasPermi('password_manage:team:remove')")
    @Log(title = "解散团队", businessType = BusinessType.DELETE)
    @DeleteMapping("/{teamId}")
    public AjaxResult remove(
            @Parameter(name = "团队id", description = "团队id", in = ParameterIn.PATH) @PathVariable Long teamId) {
        try {
            teamRoleVerifyUtils.teamSuperManagerVerify(teamId);
            return success(passwordManageTeamService.deletePasswordManageTeamById(teamId));
        } catch (ServiceException se) {
            throw se;
        } catch (Exception e) {
            log.info(e.getMessage(), e);
            throw new ServiceException(ExceptionMessages.NORMAL);
        }

    }

}
