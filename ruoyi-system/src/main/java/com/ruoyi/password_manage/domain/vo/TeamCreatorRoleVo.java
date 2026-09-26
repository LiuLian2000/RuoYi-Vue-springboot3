package com.ruoyi.password_manage.domain.vo;

/**
 * 新加团队成员时，使用此类将创建人的三要素和被添加成员的公钥传回
 * TeamCreatorRoleVo
 */
public class TeamCreatorRoleVo {

    private String randomSalt;

    private String randomIv;

    private String teamValutKeyEncryptedCipher;

    private String memberPublicKey;

    public String getRandomSalt() {
        return randomSalt;
    }

    public void setRandomSalt(String randomSalt) {
        this.randomSalt = randomSalt;
    }

    public String getRandomIv() {
        return randomIv;
    }

    public void setRandomIv(String randomIv) {
        this.randomIv = randomIv;
    }

    public String getTeamValutKeyEncryptedCipher() {
        return teamValutKeyEncryptedCipher;
    }

    public void setTeamValutKeyEncryptedCipher(String teamValutKeyEncryptedCipher) {
        this.teamValutKeyEncryptedCipher = teamValutKeyEncryptedCipher;
    }

    public String getMemberPublicKey() {
        return memberPublicKey;
    }

    public void setMemberPublicKey(String memberPublicKey) {
        this.memberPublicKey = memberPublicKey;
    }

    @Override
    public String toString() {
        return "TeamCreatorRoleVo [randomSalt=" + randomSalt + ", randomIv=" + randomIv
                + ", teamValutKeyEncryptedCipher=" + teamValutKeyEncryptedCipher + ", memberPublicKey="
                + memberPublicKey + "]";
    }

}
