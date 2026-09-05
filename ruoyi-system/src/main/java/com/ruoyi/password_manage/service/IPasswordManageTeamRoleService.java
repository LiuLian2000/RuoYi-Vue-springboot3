package com.ruoyi.password_manage.service;

import java.util.List;

import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.password_manage.domain.PasswordManageTeam;
import com.ruoyi.password_manage.domain.PasswordManageTeamRole;

/**
 * 用户在团队中的角色Service接口
 * 
 * @author DiZhicong
 * @date 2026-09-02
 */
public interface IPasswordManageTeamRoleService {
    /**
     * 查询用户在团队中的角色
     * 
     * @param id 用户在团队中的角色主键
     * @return 用户在团队中的角色
     */
    public PasswordManageTeamRole selectPasswordManageTeamRoleById(Long id);

    /**
     * 查询团队中的所有成员列表
     * 
     * @param id 团队id
     * @return 用户在团队中的所有成员列表
     */
    public List<SysUser> selectPasswordManageTeamRoleList(Long id);

    /**
     * 新增用户在团队中的角色
     * 
     * @param passwordManageTeamRole 用户在团队中的角色
     * @return 结果
     */
    public int insertPasswordManageTeamRole(PasswordManageTeamRole passwordManageTeamRole);

    /**
     * 修改用户在团队中的角色
     * 
     * @param passwordManageTeamRole 用户在团队中的角色
     * @return 结果
     */
    public int updatePasswordManageTeamRole(PasswordManageTeamRole passwordManageTeamRole);

    // /**
    // * 批量删除用户在团队中的角色
    // *
    // * @param ids 需要删除的用户在团队中的角色主键集合
    // * @return 结果
    // */
    // public int deletePasswordManageTeamRoleByIds(Long[] ids);

    /**
     * 删除用户在团队中的角色信息
     * 
     * @param teamId 团队id
     * @param userId 用户id
     * @return 结果
     */
    public int deletePasswordManageTeamRoleById(Long teamId, Long userId);

    /**
     * 获取该用户所属的所有团队
     * 
     * @param id 用户id
     * @return 所属团队列表
     */
    public List<PasswordManageTeam> selectPasswordManageTeamList(Long id);

    /***
     * 查询该成员在团队中的角色
     * 
     * @param teamId 团队id
     * @param userId 成员在password_manage_user中的id
     * @return
     */
    public Integer selectTeamRoleOfMember(Long teamId, long userId);
}
