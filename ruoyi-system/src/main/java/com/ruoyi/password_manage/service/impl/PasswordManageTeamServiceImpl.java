package com.ruoyi.password_manage.service.impl;

import java.util.List;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ruoyi.password_manage.domain.PasswordManageTeam;
import com.ruoyi.password_manage.domain.PasswordManageTeamRole;
import com.ruoyi.password_manage.domain.PasswordManageUser;
import com.ruoyi.password_manage.mapper.PasswordManageTeamMapper;
import com.ruoyi.password_manage.mapper.PasswordManageTeamRoleMapper;
import com.ruoyi.password_manage.mapper.PasswordManageUserMapper;
import com.ruoyi.password_manage.service.IPasswordManageTeamService;

/**
 * 团队密码管理-团队密码Service业务层处理
 * 
 * @author DiZhicong
 * @date 2026-09-01
 */
@Service
@Transactional
public class PasswordManageTeamServiceImpl implements IPasswordManageTeamService {
    @Autowired
    private PasswordManageTeamMapper passwordManageTeamMapper;

    @Autowired
    private PasswordManageTeamRoleMapper passwordManageTeamRoleMapper;

    @Autowired
    private PasswordManageUserMapper passwordManageUserMapper;

    /**
     * 查询团队密码管理-团队密码
     * 
     * @param id 团队密码管理-团队密码主键
     * @return 团队密码管理-团队密码
     */
    @Override
    public PasswordManageTeam selectPasswordManageTeamById(Long id) {
        return passwordManageTeamMapper.selectPasswordManageTeamById(id);
    }

    /**
     * 查询管理团队列表
     * 
     * @param id admin id
     * @return 管理团队信息集合
     */
    @Override
    public List<PasswordManageTeam> selectPasswordManageTeamList(Long id) {
        return passwordManageTeamMapper.selectPasswordManageTeamList(id);
    }

    /**
     * 新增团队密码管理-团队密码
     * 
     * @param passwordManageTeam 团队密码管理-团队密码
     * @return 结果
     */
    @Override
    public int insertPasswordManageTeam(PasswordManageTeam passwordManageTeam) {
        // 自动填充创建人信息（基于当前登录用户），不依赖前端传值
        // create_user_id 绑定 password_manage_user.id（个人密码库主键），而非 sys_user.id
        Long sysUserId = SecurityUtils.getUserId();
        PasswordManageUser pmu = passwordManageUserMapper.selectPasswordManageUserByUserId(sysUserId);
        if (pmu == null) {
            throw new ServiceException("当前用户尚未初始化个人密码库，无法创建团队");
        }
        passwordManageTeam.setCreateUserId(pmu.getId());
        SysUser sysUser = SecurityUtils.getLoginUser().getUser();
        passwordManageTeam.setUserName(sysUser.getUserName());
        passwordManageTeam.setNickName(sysUser.getNickName());
        passwordManageTeam.setCreateTime(DateUtils.getNowDate());
        int rows = passwordManageTeamMapper.insertPasswordManageTeam(passwordManageTeam);

        // 创建者自动成为该团队的管理员（team_role = 0）
        PasswordManageTeamRole creatorRole = new PasswordManageTeamRole();
        creatorRole.setTeamId(passwordManageTeam.getId());
        creatorRole.setUserId(pmu.getId());
        creatorRole.setUserName(sysUser.getUserName());
        creatorRole.setTeamName(passwordManageTeam.getTeamName());
        creatorRole.setTeamRole(0);
        creatorRole.setIsDeleted(0);
        passwordManageTeamRoleMapper.insertPasswordManageTeamRole(creatorRole);

        return rows;
    }

    /**
     * 修改团队密码管理-团队密码
     * 
     * @param passwordManageTeam 团队密码管理-团队密码
     * @return 结果
     */
    @Override
    public int updatePasswordManageTeam(PasswordManageTeam passwordManageTeam) {
        passwordManageTeam.setUpdateTime(DateUtils.getNowDate());
        return passwordManageTeamMapper.updatePasswordManageTeam(passwordManageTeam);
    }

    /**
     * 批量删除团队密码管理-团队密码
     * 
     * @param ids 需要删除的团队密码管理-团队密码主键
     * @return 结果
     */
    @Override
    public int deletePasswordManageTeamByIds(Long[] ids) {
        return passwordManageTeamMapper.deletePasswordManageTeamByIds(ids);
    }

    /**
     * 删除团队密码管理-团队密码信息
     * 
     * @param id 团队密码管理-团队密码主键
     * @return 结果
     */
    @Override
    public int deletePasswordManageTeamById(Long id) {
        passwordManageTeamMapper.deletePasswordManageTeamById(id);
        return passwordManageTeamRoleMapper.deletePasswordManageTeamAllMember(id);

    }
}
