package com.ruoyi.password_manage.domain.vo;

import com.ruoyi.common.annotation.Excel;

public class PasswordManageTeamCredentialVo {

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

    /** 用户名iv */
    @Excel(name = "用户名salt")
    private String accountSalt;

    /** 用户名加密密文 */
    @Excel(name = "用户名加密密文")
    private String accountCipher;

    /** 密码iv */
    @Excel(name = "密码salt")
    private String passwordSalt;

    /** 密码加密密文 */
    @Excel(name = "密码加密密文")
    private String passwordCipher;

    /** 备注 */
    @Excel(name = "备注")
    private String remark;

    /** 团队凭据更新人的昵称 */
    @Excel(name = "团队凭据更新人的昵称")
    private String updateSysUserNickName;

    /** 逻辑删除，0表示未删除，1表示删除 */
    @Excel(name = "逻辑删除，0表示未删除，1表示删除")
    private Integer isDeleted;

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

    public String getPlatformName() {
        return platformName;
    }

    public void setPlatformName(String platformName) {
        this.platformName = platformName;
    }

    public String getAccountSalt() {
        return accountSalt;
    }

    public void setAccountSalt(String accountIv) {
        this.accountSalt = accountIv;
    }

    public String getAccountCipher() {
        return accountCipher;
    }

    public void setAccountCipher(String accountCipher) {
        this.accountCipher = accountCipher;
    }

    public String getPasswordSalt() {
        return passwordSalt;
    }

    public void setPasswordSalt(String passwordIv) {
        this.passwordSalt = passwordIv;
    }

    public String getPasswordCipher() {
        return passwordCipher;
    }

    public void setPasswordCipher(String passwordCipher) {
        this.passwordCipher = passwordCipher;
    }

    public Integer getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Integer isDeleted) {
        this.isDeleted = isDeleted;
    }

    public String getPlatformAddress() {
        return platformAddress;
    }

    public void setPlatformAddress(String platformAddress) {
        this.platformAddress = platformAddress;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "PasswordManageTeamCredentialVo [id=" + id + ", teamId=" + teamId + ", platformName=" + platformName
                + ", platformAddress=" + platformAddress + ", accountSalt=" + accountSalt + ", accountCipher="
                + accountCipher + ", passwordSalt=" + passwordSalt + ", passwordCipher=" + passwordCipher + ", remark="
                + remark + ", isDeleted=" + isDeleted + "]";
    }

    public String getUpdateUserNickName() {
        return updateSysUserNickName;
    }

    public void setUpdateUserNickName(String updateUserNickName) {
        this.updateSysUserNickName = updateUserNickName;
    }

}
