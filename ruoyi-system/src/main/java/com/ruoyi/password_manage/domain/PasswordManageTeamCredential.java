package com.ruoyi.password_manage.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 团队密码凭据增删改查对象 password_manage_team_credential
 * 
 * @author DiZhicong
 * @date 2026-09-02
 */
public class PasswordManageTeamCredential extends BaseEntity
{
    private static final long serialVersionUID = 1L;

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

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }

    public void setTeamId(Long teamId) 
    {
        this.teamId = teamId;
    }

    public Long getTeamId() 
    {
        return teamId;
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

    public void setIsDeleted(Integer isDeleted) 
    {
        this.isDeleted = isDeleted;
    }

    public Integer getIsDeleted() 
    {
        return isDeleted;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("teamId", getTeamId())
            .append("platformName", getPlatformName())
            .append("accountIv", getAccountIv())
            .append("accountCipher", getAccountCipher())
            .append("passwordIv", getPasswordIv())
            .append("passwordCipher", getPasswordCipher())
            .append("createTime", getCreateTime())
            .append("isDeleted", getIsDeleted())
            .toString();
    }
}
