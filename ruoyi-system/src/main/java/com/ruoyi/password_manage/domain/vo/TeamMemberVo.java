package com.ruoyi.password_manage.domain.vo;

public class TeamMemberVo {

    private Long passwordManageUserId;

    private String deptName;

    private String userName;

    private String phoneNumber;

    private String nickName;

    private String teamRole;

    public Long getPasswordManageUserId() {
        return passwordManageUserId;
    }

    public void setPasswordManageUserId(Long passwordManageUserId) {
        this.passwordManageUserId = passwordManageUserId;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getTeamRole() {
        return teamRole;
    }

    public void setTeamRole(String teamRole) {
        this.teamRole = teamRole;
    }

    @Override
    public String toString() {
        return "TeamMemberVo [passwordManageUserId=" + passwordManageUserId + ", deptName=" + deptName + ", userName="
                + userName + ", phoneNumber=" + phoneNumber + ", nickName=" + nickName + ", teamRole=" + teamRole + "]";
    }

}
