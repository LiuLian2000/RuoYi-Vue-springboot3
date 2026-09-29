package com.ruoyi.password_manage.domain.vo;

/**
 * 新加团队成员时，使用此类将创建人的三要素和被添加成员的公钥传回
 * TeamCreatorRoleVo
 */
public class TeamCreatorRoleVo {

    private String teamValutKeyEncryptedCipher;

    private String memberPublicKey;

    // 新添加团队成员记录在role表中的id
    private Long newMemberRoleId;

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

    public Long getNewMemberRoleId() {
        return newMemberRoleId;
    }

    public void setNewMemberRoleId(Long newMemberRoleId) {
        this.newMemberRoleId = newMemberRoleId;
    }

    @Override
    public String toString() {
        return "TeamCreatorRoleVo [teamValutKeyEncryptedCipher=" + teamValutKeyEncryptedCipher + ", memberPublicKey="
                + memberPublicKey + ", newMemberRoleId=" + newMemberRoleId + "]";
    }

}
