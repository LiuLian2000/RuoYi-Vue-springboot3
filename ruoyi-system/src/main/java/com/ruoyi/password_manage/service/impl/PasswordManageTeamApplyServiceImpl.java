package com.ruoyi.password_manage.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.password_manage.common.constants.DeletedStatus;
import com.ruoyi.password_manage.common.constants.ExceptionMessages;
import com.ruoyi.password_manage.domain.PasswordManageTeam;
import com.ruoyi.password_manage.domain.PasswordManageTeamApply;
import com.ruoyi.password_manage.domain.PasswordManageUser;
import com.ruoyi.password_manage.domain.dto.TeamApplyCreateDto;
import com.ruoyi.password_manage.domain.vo.TeamApplyVo;
import com.ruoyi.password_manage.mapper.PasswordManageTeamApplyMapper;
import com.ruoyi.password_manage.mapper.PasswordManageTeamMapper;
import com.ruoyi.password_manage.mapper.PasswordManageTeamRoleMapper;
import com.ruoyi.password_manage.service.IPasswordManageTeamApplyService;
import com.ruoyi.password_manage.service.PasswordManageUserService;
import com.ruoyi.password_manage.utils.TeamRoleVerifyUtils;

/**
 * 团队入队申请Service业务层处理
 * 
 * @author DiZhicong
 * @date 2026-10-23
 */
@Service
public class PasswordManageTeamApplyServiceImpl implements IPasswordManageTeamApplyService {

    @Autowired
    private PasswordManageTeamApplyMapper teamApplyMapper;

    @Autowired
    private PasswordManageTeamMapper passwordManageTeamMapper;

    @Autowired
    private PasswordManageTeamRoleMapper passwordManageTeamRoleMapper;

    @Autowired
    private PasswordManageUserService passwordManageUserService;

    @Autowired
    private TeamRoleVerifyUtils teamRoleVerifyUtils;

    /** 申请状态：0 待审 / 1 通过 / 2 拒绝 */
    private static final int STATUS_PENDING = 0;
    private static final int STATUS_APPROVED = 1;
    private static final int STATUS_REJECTED = 2;

    /** 解析当前登录用户的 password_manage_user.id，未开通金库则抛异常 */
    private Long currentPmUserId() {
        SysUser sysUser = SecurityUtils.getLoginUser().getUser();
        PasswordManageUser pmUser = passwordManageUserService
                .selectPasswordManageUserByUserId(sysUser.getUserId());
        if (pmUser == null) {
            throw new ServiceException(ExceptionMessages.PM_USER_NO_VAULT);
        }
        return pmUser.getId();
    }

    @Override
    public void applyJoinTeam(TeamApplyCreateDto dto) {
        if (dto.getTeamId() == null) {
            throw new ServiceException(ExceptionMessages.TEAM_NOT_EXIST);
        }
        PasswordManageUser pmUser = passwordManageUserService
                .selectPasswordManageUserByUserId(SecurityUtils.getLoginUser().getUserId());
        if (pmUser == null || StringUtils.isEmpty(pmUser.getPublicKey())) {
            throw new ServiceException(ExceptionMessages.PM_USER_NO_VAULT);
        }
        Long pmUserId = pmUser.getId();
        PasswordManageTeam team = passwordManageTeamMapper.selectPasswordManageTeamById(dto.getTeamId());
        if (team == null) {
            throw new ServiceException(ExceptionMessages.TEAM_NOT_EXIST);
        }
        // 已加入则拒绝
        if (passwordManageTeamRoleMapper.selectTeamMemberHaveExist(dto.getTeamId(), pmUserId) > 0) {
            throw new ServiceException(ExceptionMessages.TEAM_MEMBER_HAVE_EXIST);
        }
        // 已有待审申请则拒绝重复提交
        if (teamApplyMapper.countPendingApply(dto.getTeamId(), pmUserId) > 0) {
            throw new ServiceException(ExceptionMessages.APPLY_ALREADY_EXIST);
        }
        PasswordManageTeamApply apply = new PasswordManageTeamApply();
        apply.setTeamId(dto.getTeamId());
        apply.setTeamName(team.getTeamName());
        apply.setApplicantPmUserId(pmUserId);
        apply.setApplicantUserName(SecurityUtils.getLoginUser().getUser().getUserName());
        apply.setApplyReason(dto.getApplyReason());
        apply.setStatus(STATUS_PENDING);
        apply.setIsDeleted(DeletedStatus.NOT_DELETED);
        teamApplyMapper.insertPasswordManageTeamApply(apply);
    }

    @Override
    public List<TeamApplyVo> myApplyList() {
        return teamApplyMapper.selectMyApplyList(currentPmUserId());
    }

    @Override
    public List<TeamApplyVo> pendingApplyList(Long teamId) {
        teamRoleVerifyUtils.teamManagerVerify(teamId);
        return teamApplyMapper.selectPendingApplyList(teamId);
    }

    @Override
    public void approveApply(Long applyId) {
        PasswordManageTeamApply apply = teamApplyMapper.selectPasswordManageTeamApplyById(applyId);
        if (apply == null) {
            throw new ServiceException(ExceptionMessages.APPLY_NOT_EXIST);
        }
        if (apply.getStatus() != STATUS_PENDING) {
            throw new ServiceException(ExceptionMessages.APPLY_ALREADY_HANDLED);
        }
        teamRoleVerifyUtils.teamManagerVerify(apply.getTeamId());
        apply.setStatus(STATUS_APPROVED);
        apply.setReviewerPmUserId(currentPmUserId());
        apply.setReviewerUserName(SecurityUtils.getLoginUser().getUser().getUserName());
        teamApplyMapper.updatePasswordManageTeamApply(apply);
    }

    @Override
    public void rejectApply(Long applyId, String reason) {
        PasswordManageTeamApply apply = teamApplyMapper.selectPasswordManageTeamApplyById(applyId);
        if (apply == null) {
            throw new ServiceException(ExceptionMessages.APPLY_NOT_EXIST);
        }
        if (apply.getStatus() != STATUS_PENDING) {
            throw new ServiceException(ExceptionMessages.APPLY_ALREADY_HANDLED);
        }
        teamRoleVerifyUtils.teamManagerVerify(apply.getTeamId());
        apply.setStatus(STATUS_REJECTED);
        apply.setRejectReason(reason);
        apply.setReviewerPmUserId(currentPmUserId());
        apply.setReviewerUserName(SecurityUtils.getLoginUser().getUser().getUserName());
        teamApplyMapper.updatePasswordManageTeamApply(apply);
    }
}
