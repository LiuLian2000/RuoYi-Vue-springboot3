package com.ruoyi.password_manage.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.password_manage.mapper.PasswordManageTeamRoleMapper;
import com.ruoyi.password_manage.domain.PasswordManageTeamRole;
import com.ruoyi.password_manage.service.IPasswordManageTeamRoleService;

/**
 * 用户在团队中的角色Service业务层处理
 * 
 * @author DiZhicong
 * @date 2026-09-02
 */
@Service
public class PasswordManageTeamRoleServiceImpl implements IPasswordManageTeamRoleService 
{
    @Autowired
    private PasswordManageTeamRoleMapper passwordManageTeamRoleMapper;

    /**
     * 查询用户在团队中的角色
     * 
     * @param id 用户在团队中的角色主键
     * @return 用户在团队中的角色
     */
    @Override
    public PasswordManageTeamRole selectPasswordManageTeamRoleById(Long id)
    {
        return passwordManageTeamRoleMapper.selectPasswordManageTeamRoleById(id);
    }

    /**
     * 查询用户在团队中的角色列表
     * 
     * @param passwordManageTeamRole 用户在团队中的角色
     * @return 用户在团队中的角色
     */
    @Override
    public List<PasswordManageTeamRole> selectPasswordManageTeamRoleList(PasswordManageTeamRole passwordManageTeamRole)
    {
        return passwordManageTeamRoleMapper.selectPasswordManageTeamRoleList(passwordManageTeamRole);
    }

    /**
     * 新增用户在团队中的角色
     * 
     * @param passwordManageTeamRole 用户在团队中的角色
     * @return 结果
     */
    @Override
    public int insertPasswordManageTeamRole(PasswordManageTeamRole passwordManageTeamRole)
    {
        passwordManageTeamRole.setCreateTime(DateUtils.getNowDate());
        return passwordManageTeamRoleMapper.insertPasswordManageTeamRole(passwordManageTeamRole);
    }

    /**
     * 修改用户在团队中的角色
     * 
     * @param passwordManageTeamRole 用户在团队中的角色
     * @return 结果
     */
    @Override
    public int updatePasswordManageTeamRole(PasswordManageTeamRole passwordManageTeamRole)
    {
        passwordManageTeamRole.setUpdateTime(DateUtils.getNowDate());
        return passwordManageTeamRoleMapper.updatePasswordManageTeamRole(passwordManageTeamRole);
    }

    /**
     * 批量删除用户在团队中的角色
     * 
     * @param ids 需要删除的用户在团队中的角色主键
     * @return 结果
     */
    @Override
    public int deletePasswordManageTeamRoleByIds(Long[] ids)
    {
        return passwordManageTeamRoleMapper.deletePasswordManageTeamRoleByIds(ids);
    }

    /**
     * 删除用户在团队中的角色信息
     * 
     * @param id 用户在团队中的角色主键
     * @return 结果
     */
    @Override
    public int deletePasswordManageTeamRoleById(Long id)
    {
        return passwordManageTeamRoleMapper.deletePasswordManageTeamRoleById(id);
    }
}
