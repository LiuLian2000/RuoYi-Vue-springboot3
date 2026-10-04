package com.ruoyi.password_manage.domain;

import com.ruoyi.common.annotation.Excel;

/**
 * 团队密码管理-团队密码对象 password_manage_team
 * 
 * @author DiZhicong
 * @date 2026-09-01
 */
public class PasswordManageTeam {

    /** 主键id */
    private Long id;

    /** 团队名称 */
    @Excel(name = "团队名称")
    private String teamName;

    /** 创建人ID */
    @Excel(name = "创建人ID")
    private Long createUserId;

    /** 创建人账号 */
    @Excel(name = "创建人账号")
    private String userName;

    /** 创建人昵称 */
    @Excel(name = "创建人昵称")
    private String nickName;

    /** 团队当前超级管理员ID */
    @Excel(name = "团队当前超级管理员ID")
    private Long superManagerUserId;

    /** 团队备注信息 */
    private String remark;

    /** 逻辑删除 0-未删除 1-已删除 */
    @Excel(name = "逻辑删除 0-未删除 1-已删除")
    private Integer isDeleted;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setCreateUserId(Long createUserId) {
        this.createUserId = createUserId;
    }

    public Long getCreateUserId() {
        return createUserId;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getNickName() {
        return nickName;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public void setIsDeleted(Integer isDeleted) {
        this.isDeleted = isDeleted;
    }

    public Integer getIsDeleted() {
        return isDeleted;
    }

    public Long getSuperManagerUserId() {
        return superManagerUserId;
    }

    public void setSuperManagerUserId(Long superManagerUserId) {
        this.superManagerUserId = superManagerUserId;
    }

    @Override
    public String toString() {
        return "PasswordManageTeam [id=" + id + ", teamName=" + teamName + ", createUserId=" + createUserId
                + ", userName=" + userName + ", nickName=" + nickName + ", superManagerUserId=" + superManagerUserId
                + ", remark=" + remark + ", isDeleted=" + isDeleted + "]";
    }

}
