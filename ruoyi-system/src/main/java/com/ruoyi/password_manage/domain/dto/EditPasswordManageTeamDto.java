package com.ruoyi.password_manage.domain.dto;

public class EditPasswordManageTeamDto {

    private Long id;

    private String teamName;

    private String remark;

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    @Override
    public String toString() {
        return "EditPasswordManageTeamDto [id=" + id + ", teamName=" + teamName + ", remark=" + remark + "]";
    }

}
