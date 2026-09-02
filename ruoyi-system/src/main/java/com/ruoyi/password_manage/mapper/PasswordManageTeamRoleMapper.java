package com.ruoyi.password_manage.mapper;

import java.util.List;
import com.ruoyi.password_manage.domain.PasswordManageTeamRole;

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
     * 查询用户在团队中的角色列表
     * 
     * @param passwordManageTeamRole 用户在团队中的角色
     * @return 用户在团队中的角色集合
     */
    public List<PasswordManageTeamRole> selectPasswordManageTeamRoleList(PasswordManageTeamRole passwordManageTeamRole);

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
     * @param id 用户在团队中的角色主键
     * @return 结果
     */
    public int deletePasswordManageTeamRoleById(Long id);

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
}
