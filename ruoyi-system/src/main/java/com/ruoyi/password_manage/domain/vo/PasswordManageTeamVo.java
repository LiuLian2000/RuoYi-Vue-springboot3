package com.ruoyi.password_manage.domain.vo;

import com.ruoyi.common.annotation.Excel;

public class PasswordManageTeamVo {

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

    /** 团队备注信息 */
    private String remark;

    /**
     * 使用团队管理员公钥加密得到的团队金库密钥密文
     */
    private String teamValutKeyEncryptedCipher;

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

    public Long getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Long createUserId) {
        this.createUserId = createUserId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getTeamValutKeyEncryptedCipher() {
        return teamValutKeyEncryptedCipher;
    }

    public void setTeamValutKeyEncryptedCipher(String teamValutKeyEncryptedCipher) {
        this.teamValutKeyEncryptedCipher = teamValutKeyEncryptedCipher;
    }

    @Override
    public String toString() {
        return "PasswordManageTeamVo [id=" + id + ", teamName=" + teamName + ", createUserId=" + createUserId
                + ", userName=" + userName + ", nickName=" + nickName + ", remark=" + remark
                + ", teamValutKeyEncryptedCipher=" + teamValutKeyEncryptedCipher + "]";
    }

}
