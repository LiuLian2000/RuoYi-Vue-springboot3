package com.ruoyi.password_manage.domain;

import java.time.LocalDateTime;

import com.ruoyi.common.annotation.Excel;

/**
 * 团队密码凭据增删改查对象 password_manage_team_credential
 * 
 * @author DiZhicong
 * @date 2026-09-02
 */
public class PasswordManageTeamCredential {

    /** id主键 */
    private Long id;

    /** 密码所属团队id */
    @Excel(name = "密码所属团队id")
    private Long teamId;

    /** 平台名称 */
    @Excel(name = "平台名称")
    private String platformName;

    /** 用户名salt */
    @Excel(name = "用户名salt")
    private String accountSalt;

    /** 用户名加密密文 */
    @Excel(name = "用户名加密密文")
    private String accountCipher;

    /** 密码salt */
    @Excel(name = "密码salt")
    private String passwordSalt;

    /** 密码加密密文 */
    @Excel(name = "密码加密密文")
    private String passwordCipher;

    /** 密码凭据创建时间 */
    @Excel(name = "密码凭据创建时间")
    private LocalDateTime createTime;

    /** 逻辑删除，0表示未删除，1表示删除 */
    @Excel(name = "逻辑删除，0表示未删除，1表示删除")
    private Integer isDeleted;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setTeamId(Long teamId) {
        this.teamId = teamId;
    }

    public Long getTeamId() {
        return teamId;
    }

    public void setPlatformName(String platformName) {
        this.platformName = platformName;
    }

    public String getPlatformName() {
        return platformName;
    }

    public void setAccountCipher(String accountCipher) {
        this.accountCipher = accountCipher;
    }

    public String getAccountCipher() {
        return accountCipher;
    }

    public void setPasswordCipher(String passwordCipher) {
        this.passwordCipher = passwordCipher;
    }

    public String getPasswordCipher() {
        return passwordCipher;
    }

    public void setIsDeleted(Integer isDeleted) {
        this.isDeleted = isDeleted;
    }

    public Integer getIsDeleted() {
        return isDeleted;
    }

    public String getAccountSalt() {
        return accountSalt;
    }

    public void setAccountSalt(String accountSalt) {
        this.accountSalt = accountSalt;
    }

    public String getPasswordSalt() {
        return passwordSalt;
    }

    public void setPasswordSalt(String passwordSalt) {
        this.passwordSalt = passwordSalt;
    }

    @Override
    public String toString() {
        return "PasswordManageTeamCredential [id=" + id + ", teamId=" + teamId + ", platformName=" + platformName
                + ", accountSalt=" + accountSalt + ", accountCipher=" + accountCipher + ", passwordSalt=" + passwordSalt
                + ", passwordCipher=" + passwordCipher + ", isDeleted=" + isDeleted + "]";
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

}
