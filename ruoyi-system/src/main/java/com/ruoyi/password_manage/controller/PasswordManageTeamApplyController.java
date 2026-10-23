package com.ruoyi.password_manage.controller;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.password_manage.common.constants.ExceptionMessages;
import com.ruoyi.password_manage.domain.dto.TeamApplyCreateDto;
import com.ruoyi.password_manage.domain.vo.TeamApplyVo;
import com.ruoyi.password_manage.service.IPasswordManageTeamApplyService;

/**
 * 团队入队申请Controller
 * 
 * @author DiZhicong
 * @date 2026-10-23
 */
@RestController
@RequestMapping("/password_manage/team_apply")
public class PasswordManageTeamApplyController extends BaseController {

    @Autowired
    private IPasswordManageTeamApplyService teamApplyService;

    private Logger log = LoggerFactory.getLogger(PasswordManageTeamApplyController.class);

    /**
     * 申请加入团队
     */
    @PreAuthorize("@ss.hasPermi('system:passwordManage:operate')")
    @Log(title = "申请加入团队", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult apply(@RequestBody TeamApplyCreateDto dto) {
        try {
            teamApplyService.applyJoinTeam(dto);
            return success();
        } catch (ServiceException se) {
            throw se;
        } catch (Exception e) {
            log.info(e.getMessage(), e);
            throw new ServiceException(ExceptionMessages.NORMAL);
        }
    }

    /**
     * 我的申请列表
     */
    @PreAuthorize("@ss.hasPermi('system:passwordManage:operate')")
    @Log(title = "查询我的入队申请", businessType = BusinessType.QUERY)
    @GetMapping("/my")
    public AjaxResult myApplyList() {
        try {
            List<TeamApplyVo> list = teamApplyService.myApplyList();
            return success(list);
        } catch (ServiceException se) {
            throw se;
        } catch (Exception e) {
            log.info(e.getMessage(), e);
            throw new ServiceException(ExceptionMessages.NORMAL);
        }
    }

    /**
     * 某团队的待审申请列表（仅团队管理员）
     */
    @PreAuthorize("@ss.hasPermi('system:passwordManage:operate')")
    @Log(title = "查询待审入队申请", businessType = BusinessType.QUERY)
    @GetMapping("/pending")
    public AjaxResult pendingApplyList(@RequestParam Long teamId) {
        try {
            List<TeamApplyVo> list = teamApplyService.pendingApplyList(teamId);
            return success(list);
        } catch (ServiceException se) {
            throw se;
        } catch (Exception e) {
            log.info(e.getMessage(), e);
            throw new ServiceException(ExceptionMessages.NORMAL);
        }
    }

    /**
     * 通过申请（仅团队管理员）。申请人实际入队由前端走 addTeamRole 两步握手完成，此处仅置状态。
     */
    @PreAuthorize("@ss.hasPermi('system:passwordManage:operate')")
    @Log(title = "通过入队申请", businessType = BusinessType.UPDATE)
    @PostMapping("/approve")
    public AjaxResult approve(@RequestParam Long applyId) {
        try {
            teamApplyService.approveApply(applyId);
            return success();
        } catch (ServiceException se) {
            throw se;
        } catch (Exception e) {
            log.info(e.getMessage(), e);
            throw new ServiceException(ExceptionMessages.NORMAL);
        }
    }

    /**
     * 拒绝申请（仅团队管理员）
     */
    @PreAuthorize("@ss.hasPermi('system:passwordManage:operate')")
    @Log(title = "拒绝入队申请", businessType = BusinessType.UPDATE)
    @PostMapping("/reject")
    public AjaxResult reject(@RequestParam Long applyId, @RequestParam(required = false) String reason) {
        try {
            teamApplyService.rejectApply(applyId, reason);
            return success();
        } catch (ServiceException se) {
            throw se;
        } catch (Exception e) {
            log.info(e.getMessage(), e);
            throw new ServiceException(ExceptionMessages.NORMAL);
        }
    }
}
