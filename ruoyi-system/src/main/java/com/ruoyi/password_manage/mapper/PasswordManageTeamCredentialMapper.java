package com.ruoyi.password_manage.mapper;

import java.util.List;
import com.ruoyi.password_manage.domain.PasswordManageTeamCredential;

/**
 * 团队密码凭据增删改查Mapper接口
 * 
 * @author DiZhicong
 * @date 2026-09-02
 */
public interface PasswordManageTeamCredentialMapper {
    /**
     * 查询团队密码凭据增删改查
     * 
     * @param id 团队密码凭据增删改查主键
     * @return 团队密码凭据增删改查
     */
    public PasswordManageTeamCredential selectPasswordManageTeamCredentialById(Long id);

    /**
     * 查询团队密码凭据增删改查列表
     * 
     * @param teamId 团队ID
     * @return 团队密码凭据增删改查集合
     */
    public List<PasswordManageTeamCredential> selectPasswordManageTeamCredentialList(Long teamId);

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
     * 删除团队密码凭据增删改查
     * 
     * @param id 团队密码凭据增删改查主键
     * @return 结果
     */
    public int deletePasswordManageTeamCredentialById(Long id);

    /**
     * 批量删除团队密码凭据增删改查
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deletePasswordManageTeamCredentialByIds(Long[] ids);

    /**
     * 模糊查询团队密码凭据
     * 
     * @param teamId
     * @param platName
     * @return
     */
    public List<PasswordManageTeamCredential> selectPasswordManageTeamCredential(Long teamId, String platformName);
}
