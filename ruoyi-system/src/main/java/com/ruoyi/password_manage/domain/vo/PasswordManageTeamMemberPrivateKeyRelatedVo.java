package com.ruoyi.password_manage.domain.vo;

public class PasswordManageTeamMemberPrivateKeyRelatedVo {
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
        return "PasswordManageTeamMemberPrivateKeyRelatedVo [privateKeySalt=" + privateKeySalt + ", privateKeyIv="
                + privateKeyIv + ", privateKeyCipher=" + privateKeyCipher + "]";
    }

}
