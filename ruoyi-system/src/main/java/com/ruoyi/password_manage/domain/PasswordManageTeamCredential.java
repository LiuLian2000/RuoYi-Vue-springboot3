package com.ruoyi.password_manage.domain;

import java.time.LocalDateTime;

import com.ruoyi.common.annotation.Excel;

/**
 * 团队密码凭据对象 password_manage_team_credential
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

    /** 平台网址 */
    @Excel(name = "平台网址")
    private String platformAddress;

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

    /** 密码凭据修改时间 */
    @Excel(name = "密码凭据修改时间")
    private LocalDateTime updateTime;

    /** 备注 */
    @Excel(name = "备注")
    private String remark;

    @Excel(name = "凭据更新人的sysuserId")
    private Long updateSysUserId;

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

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public String getPlatformAddress() {
        return platformAddress;
    }

    public void setPlatformAddress(String platformAddress) {
        this.platformAddress = platformAddress;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "PasswordManageTeamCredential [id=" + id + ", teamId=" + teamId + ", platformName=" + platformName
                + ", platformAddress=" + platformAddress + ", accountSalt=" + accountSalt + ", accountCipher="
                + accountCipher + ", passwordSalt=" + passwordSalt + ", passwordCipher=" + passwordCipher
                + ", createTime=" + createTime + ", updateTime=" + updateTime + ", remark=" + remark + ", isDeleted="
                + isDeleted + "]";
    }

    public Long getUpdateSysUserId() {
        return updateSysUserId;
    }

    public void setUpdateSysUserId(Long updateSysUserId) {
        this.updateSysUserId = updateSysUserId;
    }

}
