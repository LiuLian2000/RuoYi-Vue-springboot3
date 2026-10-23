package com.ruoyi.password_manage.domain.vo;

import java.util.Date;

import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 团队搜索结果视图对象
 * 
 * @author DiZhicong
 * @date 2026-10-23
 */
public class TeamSearchVo extends BaseEntity {

    /** 团队id */
    private Long teamId;

    /** 团队名称 */
    private String teamName;

    /** 团队备注 */
    private String remark;

    /** 创建人昵称 */
    private String creatorNickName;

    /** 创建时间 */
    private Date createTime;

    /** 成员数量 */
    private Integer memberCount;

    /** 当前用户是否已加入该团队 */
    private Boolean joined;

    /** 当前用户是否已提交待审申请 */
    private Boolean applied;

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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getCreatorNickName() {
        return creatorNickName;
    }

    public void setCreatorNickName(String creatorNickName) {
        this.creatorNickName = creatorNickName;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getMemberCount() {
        return memberCount;
    }

    public void setMemberCount(Integer memberCount) {
        this.memberCount = memberCount;
    }

    public Boolean getJoined() {
        return joined;
    }

    public void setJoined(Boolean joined) {
        this.joined = joined;
    }

    public Boolean getApplied() {
        return applied;
    }

    public void setApplied(Boolean applied) {
        this.applied = applied;
    }

    @Override
    public String toString() {
        return "TeamSearchVo [teamId=" + teamId + ", teamName=" + teamName + ", remark=" + remark
                + ", creatorNickName=" + creatorNickName + ", createTime=" + createTime + ", memberCount=" + memberCount
                + ", joined=" + joined + ", applied=" + applied + "]";
    }
}
