package com.ruoyi.password_manage.domain;

import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 用户密码管理主，绑定系统的sys_user对象 password_manage_user
 * 
 * @author ruoyi
 * @date 2026-08-26
 */
public class PasswordManageUser extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 该密码管理用户的主键id
     */
    private Long id;

    /**
     * 该密码管理用户所对应的系统用户id，绑定sys_user.id
     */
    private Long sysUserId;

    /**
     * 随机盐，用于对个人金库密钥进行加密
     */
    private String randomSalt;

    /**
     * 随机向量，用于对金库密钥进行加密
     */
    private String encryptedVaultIv;

    /**
     * 加密后的金库密钥密文
     */
    private String encryptedVaultCipher;

    /**
     * 用户所持密钥对公钥
     */
    private String publicKey;

    /**
     * 用户所持密钥对私钥加密所使用的随机盐
     */
    private String privateKeySalt;

    /**
     * 用户所持密钥对私钥加密所使用的随机向量
     */
    private String privateKeyIv;

    /**
     * 用户所持密钥对加密私钥
     */
    private String privateKeyCipher;

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

    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    public String getPrivateKeySalt() {
        return privateKeySalt;
    }

    public void setPrivateKeySalt(String privateKeySalt) {
        this.privateKeySalt = privateKeySalt;
    }

    public String getPrivateKeyIv() {
        return privateKeyIv;
    }

    public void setPrivateKeyIv(String privateKeyIv) {
        this.privateKeyIv = privateKeyIv;
    }

    public String getPrivateKeyCipher() {
        return privateKeyCipher;
    }

    public void setPrivateKeyCipher(String privateKeyCipher) {
        this.privateKeyCipher = privateKeyCipher;
    }

    @Override
    public String toString() {
        return "PasswordManageUser [id=" + id + ", sysUserId=" + sysUserId + ", randomSalt=" + randomSalt
                + ", encryptedVaultIv=" + encryptedVaultIv + ", encryptedVaultCipher=" + encryptedVaultCipher
                + ", publicKey=" + publicKey + ", privateKeySalt=" + privateKeySalt + ", privateKeyIv=" + privateKeyIv
                + ", privateKeyCipher=" + privateKeyCipher + "]";
    }

}
