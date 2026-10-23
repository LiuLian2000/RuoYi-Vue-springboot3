package com.ruoyi.password_manage.service;

import java.util.List;
import com.ruoyi.password_manage.domain.dto.TeamApplyCreateDto;
import com.ruoyi.password_manage.domain.vo.TeamApplyVo;

/**
 * 团队入队申请Service接口
 * 
 * @author DiZhicong
 * @date 2026-10-23
 */
public interface IPasswordManageTeamApplyService {

    /**
     * 申请加入团队
     * 
     * @param dto 申请入参（团队id + 申请理由）
     */
    void applyJoinTeam(TeamApplyCreateDto dto);

    /**
     * 查询我的申请列表
     * 
     * @return 申请列表
     */
    List<TeamApplyVo> myApplyList();

    /**
     * 查询某团队的待审申请列表（仅团队管理员）
     * 
     * @param teamId 团队id
     * @return 待审申请列表
     */
    List<TeamApplyVo> pendingApplyList(Long teamId);

    /**
     * 通过申请（仅团队管理员）。申请人实际入队由前端走 addTeamRole 两步握手完成，此处仅置状态。
     * 
     * @param applyId 申请记录id
     */
    void approveApply(Long applyId);

    /**
     * 拒绝申请（仅团队管理员）
     * 
     * @param applyId 申请记录id
     * @param reason 拒绝理由
     */
    void rejectApply(Long applyId, String reason);
}
