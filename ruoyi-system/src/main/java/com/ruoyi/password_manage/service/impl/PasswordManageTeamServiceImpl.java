package com.ruoyi.password_manage.service.impl;

import java.util.List;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.bean.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.ruoyi.password_manage.domain.PasswordManageTeam;
import com.ruoyi.password_manage.domain.PasswordManageTeamRole;
import com.ruoyi.password_manage.domain.PasswordManageUser;
import com.ruoyi.password_manage.domain.vo.PasswordManageTeamVo;
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
     * 查询团队信息
     * 
     * @param id 团队id
     * @return
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
    public List<PasswordManageTeam> selectManageTeamList(Long id) {
        return passwordManageTeamMapper.selectManageTeamList(id);
    }

    /**
     * 团队密码管理
     * 
     * 新增团队，同时新增团队创建人为团队成员
     * 
     * @param passwordManageTeam 团队信息
     * @return 团队id
     */
    @Override
    public Long insertPasswordManageTeam(PasswordManageTeamVo vo) {
        // 自动填充创建人信息（基于当前登录用户），不依赖前端传值
        Long sysUserId = SecurityUtils.getUserId();
        PasswordManageUser pmu = passwordManageUserMapper.selectPasswordManageUserByUserId(sysUserId);
        if (pmu == null) {
            throw new ServiceException("当前用户尚未初始化个人密码库，无法创建团队");
        }
        PasswordManageTeam passwordManageTeam = new PasswordManageTeam();
        BeanUtils.copyProperties(vo, passwordManageTeam);
        passwordManageTeam.setCreateUserId(pmu.getId());
        SysUser sysUser = SecurityUtils.getLoginUser().getUser();
        passwordManageTeam.setUserName(sysUser.getUserName());
        passwordManageTeam.setNickName(sysUser.getNickName());
        passwordManageTeam.setCreateTime(DateUtils.getNowDate());
        try {
            passwordManageTeamMapper.insertPasswordManageTeam(passwordManageTeam);
        } catch (Exception e) {
            throw new ServiceException("团队创建失败。");
        }

        // 创建者自动成为该团队的管理员（team_role = 0）
        PasswordManageTeamRole creatorRole = new PasswordManageTeamRole();
        creatorRole.setTeamId(passwordManageTeam.getId());
        creatorRole.setUserId(pmu.getId());
        creatorRole.setUserName(sysUser.getUserName());
        creatorRole.setTeamName(passwordManageTeam.getTeamName());
        creatorRole.setTeamRole(0);
        creatorRole.setTeamValutKeyEncryptedCipher(vo.getTeamValutKeyEncryptedCipher());
        creatorRole.setIsDeleted(0);
        try {
            passwordManageTeamRoleMapper.insertPasswordManageTeamRole(creatorRole);
        } catch (Exception e) {
            throw new ServiceException("团队创建时写入团队创建成员失败。");
        }

        return passwordManageTeam.getId();
    }

    /**
     * 修改团队信息
     *
     * @param passwordManageTeam 团队密码管理-团队密码
     * @return 结果
     */
    @Override
    public int updatePasswordManageTeam(PasswordManageTeam passwordManageTeam) {
        // TODO update的updateTime？
        // passwordManageTeam.setUpdateTime(DateUtils.getNowDate());
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
     * 删除团队
     * 
     * @param id 团队id
     * @return 结果
     */
    @Override
    public int deletePasswordManageTeamById(Long teamId) {
        passwordManageTeamMapper.deletePasswordManageTeamById(teamId);
        return passwordManageTeamRoleMapper.deletePasswordManageTeamAllMember(teamId);
    }

    /**
     * 查询以成员身份所在的团队列表
     */
    @Override
    public List<PasswordManageTeam> selectTeamList(Long passwordManageUserId) {
        return passwordManageTeamMapper.selectTeamList(passwordManageUserId);
    }

    /**
     * 查询该团队管理员的passwordManageUserId
     */
    @Override
    public List<Long> selectTeamManagerPasswordManageUserIdList(Long teamId) {
        return passwordManageTeamRoleMapper.selectTeamManagerPasswordManageUserIdList(teamId);
    }

}
