package com.ruoyi.password_manage.domain.vo;

import com.ruoyi.common.annotation.Excel;

/**
 * 个人所在团队查询接口返回vo
 * MyTeamVo
 */
public class MyTeamVo {

    /** 团队id */
    private Long teamId;

    /** 团队名称 */
    @Excel(name = "团队名称")
    private String teamName;

    /** 团队当前超级管理员ID */
    @Excel(name = "团队当前超级管理员ID")
    private Long superManagerUserId;

    /** 团队当前超级管理员的用户名 */
    private String superManagerUserName;

    /** 团队当前超级管理员的昵称 */
    private String superManagerNickName;

    /** 当前用户在该团队中的角色 */
    private Integer teamRole;

    /** 团队备注信息 */
    private String remark;

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

    public Long getSuperManagerUserId() {
        return superManagerUserId;
    }

    public void setSuperManagerUserId(Long superManagerUserId) {
        this.superManagerUserId = superManagerUserId;
    }

    public String getSuperManagerUserName() {
        return superManagerUserName;
    }

    public void setSuperManagerUserName(String superManagerUserName) {
        this.superManagerUserName = superManagerUserName;
    }

    public String getSuperManagerNickName() {
        return superManagerNickName;
    }

    public void setSuperManagerNickName(String superManagerNickName) {
        this.superManagerNickName = superManagerNickName;
    }

    public Integer getTeamRole() {
        return teamRole;
    }

    public void setTeamRole(Integer teamRole) {
        this.teamRole = teamRole;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "MyTeamVo [teamId=" + teamId + ", teamName=" + teamName + ", superManagerUserId=" + superManagerUserId
                + ", superManagerUserName=" + superManagerUserName + ", superManagerNickName=" + superManagerNickName
                + ", teamRole=" + teamRole + ", remark=" + remark + "]";
    }

}
