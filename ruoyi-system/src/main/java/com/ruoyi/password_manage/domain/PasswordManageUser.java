package com.ruoyi.password_manage.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 用户密码管理主，绑定系统的sys_user对象 password_manage_user
 * 
 * @author ruoyi
 * @date 2026-08-26
 */
public class PasswordManageUser extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private Long sysUserId;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String randomSalt;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String encryptedVaultIv;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String encryptedVaultCipher;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setSysUserId(Long sysUserId) {
        this.sysUserId = sysUserId;
    }

    public Long getSysUserId() {
        return sysUserId;
    }

    public void setRandomSalt(String randomSalt) {
        this.randomSalt = randomSalt;
    }

    public String getRandomSalt() {
        return randomSalt;
    }

    public void setEncryptedVaultIv(String encryptedVaultIv) {
        this.encryptedVaultIv = encryptedVaultIv;
    }

    public String getEncryptedVaultIv() {
        return encryptedVaultIv;
    }

    public void setEncryptedVaultCipher(String encryptedVaultCipher) {
        this.encryptedVaultCipher = encryptedVaultCipher;
    }

    public String getEncryptedVaultCipher() {
        return encryptedVaultCipher;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("sysUserId", getSysUserId())
                .append("randomSalt", getRandomSalt())
                .append("encryptedVaultIv", getEncryptedVaultIv())
                .append("encryptedVaultCipher", getEncryptedVaultCipher())
                .toString();
    }
}
