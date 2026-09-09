package com.ruoyi.password_manage.mapper;

import java.util.List;

import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.password_manage.domain.PasswordManageTeam;
import com.ruoyi.password_manage.domain.PasswordManageTeamRole;
import com.ruoyi.password_manage.domain.vo.PasswordManageMemberVo;

/**
 * 用户在团队中的角色Mapper接口
 * 
 * @author DiZhicong
 * @date 2026-09-02
 */
public interface PasswordManageTeamRoleMapper {
    /**
     * 查询用户在团队中的角色
     * 
     * @param id 用户在团队中的角色主键
     * @return 用户在团队中的角色
     */
    public PasswordManageTeamRole selectPasswordManageTeamRoleById(Long id);

    /**
     * 查询用户所管理的团队列表
     * 
     * @param id admin id
     * @return 用户管理团队列表
     */
    public List<SysUser> selectPasswordManageTeamRoleList(Long id);

    /**
     * 查询可添加的成员候选列表（有个人金库、且未加入该团队的用户）
     * 
     * @param teamId 团队id
     * @return 候选成员列表
     */
    public List<PasswordManageMemberVo> selectCandidateMembers(Long teamId);

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

    /**
     * 删除用户在团队中的角色
     * 
     * @param teamId 团队id
     * @param userId 用户id
     * @return 结果
     */
    public int deletePasswordManageTeamRoleById(Long teamId, Long userId);

    /**
     * 批量删除用户在团队中的角色
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deletePasswordManageTeamRoleByIds(Long[] ids);

    /**
     * 删除团队下的所有成员
     * 
     * @param teamId 团队id
     * @return 结果
     */
    public int deletePasswordManageTeamAllMember(Long teamId);

    /***
     * 获取成员所属团队列表
     * 
     * @param id 成员id
     * @return
     */
    public List<PasswordManageTeam> selectPasswordManageTeamList(Long id);

    /***
     * 查看该成员在团队中的角色
     * 
     * @param teamId 团队id
     * @param userId user在password_manage_user表中的id
     * @return
     */
    public Integer selectTeamRoleOfMember(Long teamId, long userId);

}
