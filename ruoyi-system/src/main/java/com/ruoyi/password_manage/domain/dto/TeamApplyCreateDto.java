package com.ruoyi.password_manage.domain.dto;

import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 申请加入团队入参
 * 
 * @author DiZhicong
 * @date 2026-10-23
 */
public class TeamApplyCreateDto extends BaseEntity {

    /** 团队id */
    private Long teamId;

    /** 申请理由 */
    private String applyReason;

    public Long getTeamId() {
        return teamId;
    }

    public void setTeamId(Long teamId) {
        this.teamId = teamId;
    }

    public String getApplyReason() {
        return applyReason;
    }

    public void setApplyReason(String applyReason) {
        this.applyReason = applyReason;
    }

    @Override
    public String toString() {
        return "TeamApplyCreateDto [teamId=" + teamId + ", applyReason=" + applyReason + "]";
    }
}
