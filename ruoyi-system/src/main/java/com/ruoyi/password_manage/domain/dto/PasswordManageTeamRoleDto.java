package com.ruoyi.password_manage.domain.dto;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

public class PasswordManageTeamRoleDto extends BaseEntity {

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
    @Excel(name = "用户的passwordManageUserId")
    private Long userId;

    /** 该成员在团队中的角色，0-admin，1-mebmer */
    @Excel(name = "该成员在团队中的角色，0-admin，1-mebmer")
    private Integer teamRole;

    /** 是否删除 */
    @Excel(name = "是否删除")
    private Integer isDeleted;

    /**
     * 创建人使用公钥加密后的团队金库密钥
     */
    private String teamValutKeyEncryptedCipher;

    /**
     * 操作人所在团队id 废弃字段
     */
    private Long operatedTeamId;

    /**
     * 操作人的userid 废弃字段
     */
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

    public String getTeamValutKeyEncryptedCipher() {
        return teamValutKeyEncryptedCipher;
    }

    public void setTeamValutKeyEncryptedCipher(String teamValutKeyEncryptedCipher) {
        this.teamValutKeyEncryptedCipher = teamValutKeyEncryptedCipher;
    }

    @Override
    public String toString() {
        return "PasswordManageTeamRoleVo [id=" + id + ", teamName=" + teamName + ", teamId=" + teamId + ", userName="
                + userName + ", userId=" + userId + ", teamRole=" + teamRole + ", isDeleted=" + isDeleted
                + ", teamValutKeyEncryptedCipher=" + teamValutKeyEncryptedCipher + ", operatedTeamId=" + operatedTeamId
                + ", operatedUserId=" + operatedUserId + "]";
    }

}
