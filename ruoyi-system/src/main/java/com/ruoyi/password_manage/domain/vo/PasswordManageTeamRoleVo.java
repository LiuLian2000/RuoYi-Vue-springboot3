package com.ruoyi.password_manage.domain.vo;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

public class PasswordManageTeamRoleVo extends BaseEntity {

    /** 主键 */
    private Long id;

    /** 团队名称 */
    @Excel(name = "团队名称")
    private String teamName;

    /** 团队id */
    @Excel(name = "团队id")
    private Long teamId;

    /** 成员用户名 */
    @Excel(name = "成员用户名")
    private String userName;

    /** 成员用户id */
    @Excel(name = "成员用户id")
    private Long userId;

    /** 该成员在团队中的角色，0-admin，1-mebmer */
    @Excel(name = "该成员在团队中的角色，0-admin，1-mebmer")
    private Integer teamRole;

    /** 是否删除 */
    @Excel(name = "是否删除")
    private Integer isDeleted;

    /** 操作人所在团队id */
    private Long operatedTeamId;

    /** 操作人的userid */
    private Long operatedUserId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public Long getTeamId() {
        return teamId;
    }

    public void setTeamId(Long teamId) {
        this.teamId = teamId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getTeamRole() {
        return teamRole;
    }

    public void setTeamRole(Integer teamRole) {
        this.teamRole = teamRole;
    }

    public Integer getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Integer isDeleted) {
        this.isDeleted = isDeleted;
    }

    public Long getOperatedTeamId() {
        return operatedTeamId;
    }

    public void setOperatedTeamId(Long operatedTeamId) {
        this.operatedTeamId = operatedTeamId;
    }

    public Long getOperatedUserId() {
        return operatedUserId;
    }

    public void setOperatedUserId(Long operatedUserId) {
        this.operatedUserId = operatedUserId;
    }

    @Override
    public String toString() {
        return "PasswordManageTeamRoleVo [id=" + id + ", teamName=" + teamName + ", teamId=" + teamId + ", userName="
                + userName + ", userId=" + userId + ", teamRole=" + teamRole + ", isDeleted=" + isDeleted
                + ", operatedTeamId=" + operatedTeamId + ", operatedUserId=" + operatedUserId + ", getCreateTime()="
                + getCreateTime() + "]";
    }

}
