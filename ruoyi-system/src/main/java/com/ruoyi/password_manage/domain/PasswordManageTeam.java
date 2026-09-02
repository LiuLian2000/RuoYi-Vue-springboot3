package com.ruoyi.password_manage.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 团队密码管理-团队密码对象 password_manage_team
 * 
 * @author DiZhicong
 * @date 2026-09-01
 */
public class PasswordManageTeam extends BaseEntity {
    private static final long serialVersionUID = 1L;

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

    /** 团队密钥加密salt */
    @Excel(name = "团队密钥加密salt，前端传回，不予展示")
    private String randomSalt;

    /** 团队密钥加密随机iv */
    @Excel(name = "团队密钥加密随机iv，前端传回，不予展示")
    private String encryptedTeamKeyIv;

    /** 团队密钥加密后密文 */
    @Excel(name = "团队密钥加密后密文，前端传回，不予展示")
    private String encryptedTeamKeyCipher;

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

    public void setRandomSalt(String randomSalt) {
        this.randomSalt = randomSalt;
    }

    public String getRandomSalt() {
        return randomSalt;
    }

    public void setEncryptedTeamKeyIv(String encryptedTeamKeyIv) {
        this.encryptedTeamKeyIv = encryptedTeamKeyIv;
    }

    public String getEncryptedTeamKeyIv() {
        return encryptedTeamKeyIv;
    }

    public void setEncryptedTeamKeyCipher(String encryptedTeamKeyCipher) {
        this.encryptedTeamKeyCipher = encryptedTeamKeyCipher;
    }

    public String getEncryptedTeamKeyCipher() {
        return encryptedTeamKeyCipher;
    }

    public void setIsDeleted(Integer isDeleted) {
        this.isDeleted = isDeleted;
    }

    public Integer getIsDeleted() {
        return isDeleted;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("teamName", getTeamName())
                .append("createUserId", getCreateUserId())
                .append("userName", getUserName())
                .append("nickName", getNickName())
                .append("randomSalt", getRandomSalt())
                .append("encryptedTeamKeyIv", getEncryptedTeamKeyIv())
                .append("encryptedTeamKeyCipher", getEncryptedTeamKeyCipher())
                .append("remark", getRemark())
                .append("createTime", getCreateTime())
                .append("updateTime", getUpdateTime())
                .append("isDeleted", getIsDeleted())
                .toString();
    }
}
