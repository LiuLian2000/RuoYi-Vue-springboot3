package com.ruoyi.password_manage.service;

import java.util.List;

import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.password_manage.domain.PasswordManageTeam;
import com.ruoyi.password_manage.domain.PasswordManageTeamRole;
import com.ruoyi.password_manage.domain.vo.PasswordManageTeamRoleVo;
import com.ruoyi.password_manage.domain.vo.TeamCandidateMemberVo;
import com.ruoyi.password_manage.domain.vo.TeamCreatorRoleVo;

/**
 * 
 * 用户在团队中的角色Service接口
 * 
 * @author DiZhicong
 * @date 2026-09-02
 */
// TODO 后续应该改造为用户在团队中的信息
public interface IPasswordManageTeamRoleService {
    /**
     * 查询用户在团队中的角色信息
     * 
     * @param id 用户在团队中的角色主键
     * @return 用户在团队中的角色
     */
    public PasswordManageTeamRole selectPasswordManageTeamRoleById(Long id);

    /**
     * 查询用户在团队中的角色信息
     * 
     * @param passwordManageUserId 用户在password_manage_user表中的id
     * @return
     */
    public PasswordManageTeamRole selectPasswordManageTeamRoleByPasswordManageUserId(Long teamId,
            Long passwordManageUserId);

    /**
     * 查询团队中的所有成员列表
     * 
     * @param id 团队id
     * @return 用户在团队中的所有成员列表
     */
    public List<SysUser> selectPasswordManageTeamRoleList(Long id);

    /**
     * 查询可添加的成员候选列表（有个人金库、且未加入该团队的用户）
     * 
     * @param teamId 团队id
     * @return 候选成员列表
     */
    public List<TeamCandidateMemberVo> selectCandidateMembers(Long teamId);

    /**
     * 新增用户在团队中的角色
     * 
     * @param passwordManageTeamRole 用户在团队中的角色
     * @return 结果
     */
    public int insertPasswordManageTeamRole(PasswordManageTeamRole passwordManageTeamRole);

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
     * @param id 成员在password_manage_user中的id
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

    /**
     * 新增团队成员
     * 
     * @param vo
     * @return
     */
    public TeamCreatorRoleVo addTeamMember(PasswordManageTeamRoleVo vo, Long teamCreatorPasswordManageUserId);

    /**
     * 团队成员退出团队
     * 
     * @param teamId
     * @param passwordManageUserId
     * @return
     */
    public Integer MemberLeaveTeam(Long teamId, Long passwordManageUserId);

    /**
     * 团队成员添加管理员权限
     * 
     * @param teamId
     * @param passwordManageUserId
     * @return
     */
    public Integer setTeamManagerAuth(Long teamId, Long passwordManageUserId);

    /**
     * 团队成员移除管理员权限
     * 
     * @param teamId
     * @param PasswordManagerUserId
     * @return
     */
    public Integer removeTeamManagerAuth(Long teamId, Long PasswordManageUserId);

    /**
     * 移交团队超级管理员权限
     * 
     * @param teamId
     * @param passowrdManageUserId
     * @return
     */
    public Integer transTeamSuperManagerAuth(Long teamId, Long passowrdManageUserId, Long nowSuperAdminUserId);

}
