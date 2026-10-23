package com.ruoyi.password_manage.domain.vo;

import java.util.Date;

import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 团队入队申请视图对象
 * 
 * @author DiZhicong
 * @date 2026-10-23
 */
public class TeamApplyVo extends BaseEntity {

    /** 申请记录主键 */
    private Long id;

    /** 团队id */
    private Long teamId;

    /** 团队名称 */
    private String teamName;

    /** 申请人 password_manage_user.id */
    private Long applicantPmUserId;

    /** 申请人用户名 */
    private String applicantUserName;

    /** 申请理由 */
    private String applyReason;

    /** 0待审 1通过 2拒绝 */
    private Integer status;

    /** 拒绝理由 */
    private String rejectReason;

    /** 审批人 password_manage_user.id */
    private Long reviewerPmUserId;

    /** 审批人用户名 */
    private String reviewerUserName;

    /** 申请时间 */
    private Date createTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTeamId() {
        return teamId;
    }

    public void setTeamId(Long teamId) {
        this.teamId = teamId;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public Long getApplicantPmUserId() {
        return applicantPmUserId;
    }

    public void setApplicantPmUserId(Long applicantPmUserId) {
        this.applicantPmUserId = applicantPmUserId;
    }

    public String getApplicantUserName() {
        return applicantUserName;
    }

    public void setApplicantUserName(String applicantUserName) {
        this.applicantUserName = applicantUserName;
    }

    public String getApplyReason() {
        return applyReason;
    }

    public void setApplyReason(String applyReason) {
        this.applyReason = applyReason;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getRejectReason() {
        return rejectReason;
    }

    public void setRejectReason(String rejectReason) {
        this.rejectReason = rejectReason;
    }

    public Long getReviewerPmUserId() {
        return reviewerPmUserId;
    }

    public void setReviewerPmUserId(Long reviewerPmUserId) {
        this.reviewerPmUserId = reviewerPmUserId;
    }

    public String getReviewerUserName() {
        return reviewerUserName;
    }

    public void setReviewerUserName(String reviewerUserName) {
        this.reviewerUserName = reviewerUserName;
    }

    @Override
    public Date getCreateTime() {
        return createTime;
    }

    @Override
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "TeamApplyVo [id=" + id + ", teamId=" + teamId + ", teamName=" + teamName
                + ", applicantPmUserId=" + applicantPmUserId + ", applicantUserName=" + applicantUserName
                + ", applyReason=" + applyReason + ", status=" + status + ", rejectReason=" + rejectReason
                + ", reviewerPmUserId=" + reviewerPmUserId + ", reviewerUserName=" + reviewerUserName
                + ", createTime=" + createTime + "]";
    }
}
