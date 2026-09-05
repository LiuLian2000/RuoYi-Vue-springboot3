package com.ruoyi.password_manage.service;

import java.util.List;
import com.ruoyi.password_manage.domain.PasswordManageTeamCredential;

/**
 * 团队密码凭据增删改查Service接口
 * 
 * @author DiZhicong
 * @date 2026-09-02
 */
public interface IPasswordManageTeamCredentialService {
    /**
     * 查询团队密码凭据增删改查
     * 
     * @param id 团队密码凭据增删改查主键
     * @return 团队密码凭据增删改查
     */
    public PasswordManageTeamCredential selectPasswordManageTeamCredentialById(Long id);

    /**
     * 查询团队密码凭据
     * 
     * @param teamId 团队id
     * @return 团队密码凭据集合
     */
    public List<PasswordManageTeamCredential> selectPasswordManageTeamCredentialList(Long teamId);

    /***
     * 模糊查询团队密码
     * 
     * @param passwordManageTeamCredential
     * @return
     */
    public List<PasswordManageTeamCredential> selectPasswordManageTeamCredential(
            Long teamId, String platName);

    /**
     * 新增团队密码凭据增删改查
     * 
     * @param passwordManageTeamCredential 团队密码凭据增删改查
     * @return 结果
     */
    public int insertPasswordManageTeamCredential(PasswordManageTeamCredential passwordManageTeamCredential);

    /**
     * 修改团队密码凭据增删改查
     * 
     * @param passwordManageTeamCredential 团队密码凭据增删改查
     * @return 结果
     */
    public int updatePasswordManageTeamCredential(PasswordManageTeamCredential passwordManageTeamCredential);

    /**
     * 批量删除团队密码凭据增删改查
     * 
     * @param ids 需要删除的团队密码凭据增删改查主键集合
     * @return 结果
     */
    public int deletePasswordManageTeamCredentialByIds(Long[] ids);

    /**
     * 删除团队密码凭据增删改查信息
     * 
     * @param id 团队密码凭据增删改查主键
     * @return 结果
     */
    public int deletePasswordManageTeamCredentialById(Long id);
}
