package com.ruoyi.password_manage.domain.vo;

public class TeamCandidateMemberVo {

    private Long passwordManageUserId;

    private String deptName;

    private String userName;

    private String phoneNumber;

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

    @Override
    public String toString() {
        return "TeamCandidateMemberVo [passwordManageUserId=" + passwordManageUserId + ", deptName=" + deptName
                + ", userName=" + userName + ", phoneNumber=" + phoneNumber + "]";
    }

}
