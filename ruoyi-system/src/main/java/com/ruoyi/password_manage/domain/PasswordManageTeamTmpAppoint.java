package com.ruoyi.password_manage.domain;

import java.time.LocalDateTime;

/**
 * password_manage_team_tmp_appoint实体类
 * PasswordManageTeamTmpAppoint
 */
public class PasswordManageTeamTmpAppoint {

    private Long id;

    private Long teamId;

    private Long sysusrId;

    private String encryptedCipher;

    private int isDeleted;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public int getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(int isDeleted) {
        this.isDeleted = isDeleted;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return "PasswordManageTeamTmpAppoint [id=" + id + ", teamId=" + teamId + ", sysusrId=" + sysusrId
                + ", encryptedCipher=" + encryptedCipher + ", isDeleted=" + isDeleted + ", createTime=" + createTime
                + ", updateTime=" + updateTime + "]";
    }

}
