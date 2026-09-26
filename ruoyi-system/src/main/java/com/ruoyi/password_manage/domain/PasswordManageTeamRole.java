package com.ruoyi.password_manage.domain;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 用户在团队中的角色对象 password_manage_team_role
 * 
 * @author DiZhicong
 * @date 2026-09-02
 */
public class PasswordManageTeamRole extends BaseEntity {
    private static final long serialVersionUID = 1L;

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

    /**
     * 用户加密团队金库密钥所使用的随机盐
     */
    private String randomSalt;

    /**
     * 用户加密团队金库密钥所使用的随机向量
     */
    private String randomIv;

    /**
     * 用户加密团队金库密钥后的密文
     */
    private String teamValutKeyEncryptedCipher;

    /** 是否删除 */
    @Excel(name = "是否删除")
    private Integer isDeleted;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamId(Long teamId) {
        this.teamId = teamId;
    }

    public Long getTeamId() {
        return teamId;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setTeamRole(Integer teamRole) {
        this.teamRole = teamRole;
    }

    public Integer getTeamRole() {
        return teamRole;
    }

    public void setIsDeleted(Integer isDeleted) {
        this.isDeleted = isDeleted;
    }

    public Integer getIsDeleted() {
        return isDeleted;
    }

    public String getRandomSalt() {
        return randomSalt;
    }

    public void setRandomSalt(String randomSalt) {
        this.randomSalt = randomSalt;
    }

    public String getRandomIv() {
        return randomIv;
    }

    public void setRandomIv(String randomIv) {
        this.randomIv = randomIv;
    }

    public String getTeamValutKeyEncryptedCipher() {
        return teamValutKeyEncryptedCipher;
    }

    public void setTeamValutKeyEncryptedCipher(String teamValutKeyEncryptedCipher) {
        this.teamValutKeyEncryptedCipher = teamValutKeyEncryptedCipher;
    }

    @Override
    public String toString() {
        return "PasswordManageTeamRole [id=" + id + ", teamName=" + teamName + ", teamId=" + teamId + ", userName="
                + userName + ", userId=" + userId + ", teamRole=" + teamRole + ", randomSalt=" + randomSalt
                + ", randomIv=" + randomIv + ", teamValutKeyEncryptedCipher=" + teamValutKeyEncryptedCipher
                + ", isDeleted=" + isDeleted + "]";
    }

}
