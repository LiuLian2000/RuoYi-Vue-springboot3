package com.ruoyi.password_manage.domain.vo;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

public class PasswordManageTeamCredentialVo extends BaseEntity {

    /** id主键 */
    private Long id;

    /** 密码所属团队id */
    @Excel(name = "密码所属团队id")
    private Long teamId;

    /** 平台名称 */
    @Excel(name = "平台名称")
    private String platformName;

    /** 用户名iv */
    @Excel(name = "用户名iv")
    private String accountIv;

    /** 用户名加密密文 */
    @Excel(name = "用户名加密密文")
    private String accountCipher;

    /** 密码iv */
    @Excel(name = "密码iv")
    private String passwordIv;

    /** 密码加密密文 */
    @Excel(name = "密码加密密文")
    private String passwordCipher;

    /** 逻辑删除，0表示未删除，1表示删除 */
    @Excel(name = "逻辑删除，0表示未删除，1表示删除")
    private Integer isDeleted;

    /** 操作人所属团队id */
    private Long operatedTeamId;

    /** 操作人id */
    private Long operatedUserId;

    private String authPassword;

    public String getAuthPassword() {
        return authPassword;
    }

    public void setAuthPassword(String password) {
        this.authPassword = password;
    }

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

    public String getAccountIv() {
        return accountIv;
    }

    public void setAccountIv(String accountIv) {
        this.accountIv = accountIv;
    }

    public String getAccountCipher() {
        return accountCipher;
    }

    public void setAccountCipher(String accountCipher) {
        this.accountCipher = accountCipher;
    }

    public String getPasswordIv() {
        return passwordIv;
    }

    public void setPasswordIv(String passwordIv) {
        this.passwordIv = passwordIv;
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

}
