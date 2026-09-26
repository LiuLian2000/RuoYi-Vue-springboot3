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

    @Override
    public String toString() {
        return "UserLoginVo [Token=" + Token + ", privateKetSalt=" + privateKetSalt + ", privateKeyIv=" + privateKeyIv
                + ", privateKeyCipher=" + privateKeyCipher + "]";
    }

}
