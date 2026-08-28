package com.ruoyi.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 用户密码管理子，绑定password_manage_user对象 password_manage_user_credential
 * 
 * @author ruoyi
 * @date 2026-08-26
 */
public class PasswordManageUserCredential extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private Long passwordManageUserId;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String platformName;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String accountIv;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String accountCipher;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String passwordIv;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String passwordCipher;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }

    public void setPasswordManageUserId(Long passwordManageUserId) 
    {
        this.passwordManageUserId = passwordManageUserId;
    }

    public Long getPasswordManageUserId() 
    {
        return passwordManageUserId;
    }

    public void setPlatformName(String platformName) 
    {
        this.platformName = platformName;
    }

    public String getPlatformName() 
    {
        return platformName;
    }

    public void setAccountIv(String accountIv) 
    {
        this.accountIv = accountIv;
    }

    public String getAccountIv() 
    {
        return accountIv;
    }

    public void setAccountCipher(String accountCipher) 
    {
        this.accountCipher = accountCipher;
    }

    public String getAccountCipher() 
    {
        return accountCipher;
    }

    public void setPasswordIv(String passwordIv) 
    {
        this.passwordIv = passwordIv;
    }

    public String getPasswordIv() 
    {
        return passwordIv;
    }

    public void setPasswordCipher(String passwordCipher) 
    {
        this.passwordCipher = passwordCipher;
    }

    public String getPasswordCipher() 
    {
        return passwordCipher;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("passwordManageUserId", getPasswordManageUserId())
            .append("platformName", getPlatformName())
            .append("accountIv", getAccountIv())
            .append("accountCipher", getAccountCipher())
            .append("passwordIv", getPasswordIv())
            .append("passwordCipher", getPasswordCipher())
            .append("createTime", getCreateTime())
            .toString();
    }
}
