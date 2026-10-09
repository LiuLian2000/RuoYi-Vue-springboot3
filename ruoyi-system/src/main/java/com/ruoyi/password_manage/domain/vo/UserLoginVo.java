package com.ruoyi.password_manage.domain.vo;

/**
 * 用户登陆vo
 * UserLoginVo
 */
public class UserLoginVo {

    /**
     * 用户登陆返回token
     */
    private String Token;

    /**
     * 登陆用户私钥加密所使用的随机盐
     */
    private String privateKetSalt;

    /**
     * 登陆用户私钥加密所使用的随机向量
     */
    private String privateKeyIv;

    /**
     * 登陆用户私钥加密后的密文
     */
    private String privateKeyCipher;

    /**
     * 登录用户个人金库秘钥加密所使用的随机盐
     */
    private String personalVaultSalt;

    /**
     * 登录用户个人金库秘钥加密所使用的随机向量
     */
    private String personalVaultIv;

    /**
     * 登录用户个人金库秘钥加密后的密文
     */
    private String personalVaultCipher;

    public String getToken() {
        return Token;
    }

    public void setToken(String token) {
        Token = token;
    }

    public String getPrivateKetSalt() {
        return privateKetSalt;
    }

    public void setPrivateKetSalt(String privateKetSalt) {
        this.privateKetSalt = privateKetSalt;
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

    public String getPersonalVaultSalt() {
        return personalVaultSalt;
    }

    public void setPersonalVaultSalt(String personalVaultSalt) {
        this.personalVaultSalt = personalVaultSalt;
    }

    public String getPersonalVaultIv() {
        return personalVaultIv;
    }

    public void setPersonalVaultIv(String personalVaultIv) {
        this.personalVaultIv = personalVaultIv;
    }

    public String getPersonalVaultCipher() {
        return personalVaultCipher;
    }

    public void setPersonalVaultCipher(String personalVaultCipher) {
        this.personalVaultCipher = personalVaultCipher;
    }

    @Override
    public String toString() {
        return "UserLoginVo [Token=" + Token + ", privateKetSalt=" + privateKetSalt + ", privateKeyIv=" + privateKeyIv
                + ", privateKeyCipher=" + privateKeyCipher + ", personalVaultSalt=" + personalVaultSalt
                + ", personalVaultIv=" + personalVaultIv + ", personalVaultCipher=" + personalVaultCipher + "]";
    }

}
