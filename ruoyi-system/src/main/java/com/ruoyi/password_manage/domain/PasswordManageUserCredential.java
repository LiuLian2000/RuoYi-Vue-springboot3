package com.ruoyi.password_manage.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;

/**
 * 用户密码管理子，绑定password_manage_user对象 password_manage_user_credential
 * 
 * @author ruoyi
 * @date 2026-08-26
 */
public class PasswordManageUserCredential {

    /** 主键id */
    private Long id;

    /** 密码归属用户password_manage_user的id */
    @Excel(name = "密码归属用户password_manage_user的id")
    private Long passwordManageUserId;

    /** 平台名称 */
    @Excel(name = "平台名称")
    private String platformName;

    /** 平台地址 */
    @Excel(name = "平台地址")
    private String platformAddress;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String accountIv;

    /** 用户名密文 */
    @Excel(name = "用户名密文")
    private String accountCipher;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String passwordIv;

    /** 密码密文 */
    @Excel(name = "密码密文")
    private String passwordCipher;

    /** $column.columnComment */
    @Excel(name = "备注")
    private String remark;

    @Excel(name = "更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

    @Excel(name = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setPasswordManageUserId(Long passwordManageUserId) {
        this.passwordManageUserId = passwordManageUserId;
    }

    public Long getPasswordManageUserId() {
        return passwordManageUserId;
    }

    public void setPlatformName(String platformName) {
        this.platformName = platformName;
    }

    public String getPlatformName() {
        return platformName;
    }

    public void setAccountIv(String accountIv) {
        this.accountIv = accountIv;
    }

    public String getAccountIv() {
        return accountIv;
    }

    public void setAccountCipher(String accountCipher) {
        this.accountCipher = accountCipher;
    }

    public String getAccountCipher() {
        return accountCipher;
    }

    public void setPasswordIv(String passwordIv) {
        this.passwordIv = passwordIv;
    }

    public String getPasswordIv() {
        return passwordIv;
    }

    public void setPasswordCipher(String passwordCipher) {
        this.passwordCipher = passwordCipher;
    }

    public String getPasswordCipher() {
        return passwordCipher;
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

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "PasswordManageUserCredential [id=" + id + ", passwordManageUserId=" + passwordManageUserId
                + ", platformName=" + platformName + ", platformAddress=" + platformAddress + ", accountIv=" + accountIv
                + ", accountCipher=" + accountCipher + ", passwordIv=" + passwordIv + ", passwordCipher="
                + passwordCipher + ", remark=" + remark + ", updateTime=" + updateTime + ", createTime=" + createTime
                + "]";
    }

}
