package com.ruoyi.password_manage.domain.dto;

public class PasswordManageTeamTmpAppointDto {

    private Long teamId;

    private Long sysusrId;

    private String encryptedCipher;

    public Long getTeamId() {
        return teamId;
    }

    public void setTeamId(Long teamId) {
        this.teamId = teamId;
    }

    public Long getSysusrId() {
        return sysusrId;
    }

    public void setSysusrId(Long sysusrId) {
        this.sysusrId = sysusrId;
    }

    public String getEncryptedCipher() {
        return encryptedCipher;
    }

    public void setEncryptedCipher(String encryptedCipher) {
        this.encryptedCipher = encryptedCipher;
    }

    @Override
    public String toString() {
        return "PasswordManageTeamTmpAppointDto [teamId=" + teamId + ", sysusrId=" + sysusrId + ", encryptedCipher="
                + encryptedCipher + "]";
    }

}
