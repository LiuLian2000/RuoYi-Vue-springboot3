package com.ruoyi.password_manage.domain.vo;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;

public class MyManagementTeamVo {

    /** 主键id */
    private Long teamId;

    /** 团队名称 */
    @Excel(name = "团队名称")
    private String teamName;

    /** 团队当前超级管理员ID */
    @Excel(name = "团队当前超级管理员ID")
    private Long superManagerUserId;

    /** 团队当前超级管理员用户名 */
    private String superManagerUserName;

    /** 团队当前超级管理员昵称 */
    private String superManagerNickName;

    @Excel(name = "团队创建人昵称")
    private String createUserNickName;

    @Excel(name = "团队创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    /** 用户在该团队中的角色 */
    private Integer teamRole;

    /** 团队备注信息 */
    private String remark;

    public Long getTeamId() {
        return teamId;
    }

    public void setTeamId(Long id) {
        this.teamId = id;
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

    public String getCreateUserNickName() {
        return createUserNickName;
    }

    public void setCreateUserNickName(String createUserNickName) {
        this.createUserNickName = createUserNickName;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "MyManagementTeamVo [teamId=" + teamId + ", teamName=" + teamName + ", superManagerUserId="
                + superManagerUserId + ", superManagerUserName=" + superManagerUserName + ", superManagerNickName="
                + superManagerNickName + ", createUserNickName=" + createUserNickName + ", createTime=" + createTime
                + ", teamRole=" + teamRole + ", remark=" + remark + "]";
    }

}
