package com.ruoyi.password_manage.domain.vo;

import java.util.List;

import com.ruoyi.password_manage.domain.PasswordManageTeamCredential;

/**
 * 获取团队凭据vo
 * TeamCredentialVo
 */
public class TeamCredentialVo {

    /**
     * 该成员在目标团队中使用个人公钥加密所得到的团队金库密钥密文
     */
    private String teamValutKeyEncryptedCipher;

    private List<PasswordManageTeamCredential> teamCredentials;

    public String getTeamValutKeyEncryptedCipher() {
        return teamValutKeyEncryptedCipher;
    }

    public void setTeamValutKeyEncryptedCipher(String teamValutKeyEncryptedCipher) {
        this.teamValutKeyEncryptedCipher = teamValutKeyEncryptedCipher;
    }

    public List<PasswordManageTeamCredential> getTeamCredentials() {
        return teamCredentials;
    }

    public void setTeamCredentials(List<PasswordManageTeamCredential> teamCredentials) {
        this.teamCredentials = teamCredentials;
    }

    @Override
    public String toString() {
        return "TeamCredentialVo [teamValutKeyEncryptedCipher=" + teamValutKeyEncryptedCipher + ", teamCredentials="
                + teamCredentials + "]";
    }

}
