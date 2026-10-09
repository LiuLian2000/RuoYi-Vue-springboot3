package com.ruoyi.password_manage.service.impl;

import java.util.List;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.bean.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.ruoyi.password_manage.common.constants.DeletedStatus;
import com.ruoyi.password_manage.common.constants.ExceptionMessages;
import com.ruoyi.password_manage.common.constants.TeamRole;
import com.ruoyi.password_manage.domain.PasswordManageTeam;
import com.ruoyi.password_manage.domain.PasswordManageTeamRole;
import com.ruoyi.password_manage.domain.PasswordManageUser;
import com.ruoyi.password_manage.domain.dto.EditPasswordManageTeamDto;
import com.ruoyi.password_manage.domain.vo.MyManagementTeamVo;
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
    public List<MyManagementTeamVo> selectManageTeamList(Long id) {
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
            throw new ServiceException(ExceptionMessages.VAULT_UNLOCK);
        }
        if (passwordManageTeamMapper.selectTeamByTeamName(vo.getTeamName()) > 0) {
            throw new ServiceException(ExceptionMessages.TEAM_NAME_HAVE_EXIST);
        }
        PasswordManageTeam passwordManageTeam = new PasswordManageTeam();
        BeanUtils.copyProperties(vo, passwordManageTeam);
        passwordManageTeam.setCreateUserId(pmu.getId());
        passwordManageTeam.setSuperManagerUserId(pmu.getId());
        SysUser sysUser = SecurityUtils.getLoginUser().getUser();
        passwordManageTeam.setUserName(sysUser.getUserName());
        passwordManageTeam.setNickName(sysUser.getNickName());
        passwordManageTeamMapper.insertPasswordManageTeam(passwordManageTeam);
        // 创建者自动成为该团队的超级管理员（team_role = 0）
        PasswordManageTeamRole creatorRole = new PasswordManageTeamRole();
        creatorRole.setTeamId(passwordManageTeam.getId());
        creatorRole.setUserId(pmu.getId());
        creatorRole.setUserName(sysUser.getUserName());
        creatorRole.setTeamName(passwordManageTeam.getTeamName());
        creatorRole.setTeamRole(TeamRole.SUPER_ADMIN);
        creatorRole.setTeamValutKeyEncryptedCipher(vo.getTeamValutKeyEncryptedCipher());
        creatorRole.setIsDeleted(DeletedStatus.NOT_DELETED);
        passwordManageTeamRoleMapper.insertPasswordManageTeamRole(creatorRole);
        return passwordManageTeam.getId();
    }

    /**
     * 修改团队信息
     *
     * @param passwordManageTeam 团队密码管理-团队密码
     * @return 结果
     */
    @Override
    public int updatePasswordManageTeam(EditPasswordManageTeamDto dto) {
        if (passwordManageTeamMapper.selectPasswordManageTeamById(dto.getId()) == null) {
            throw new ServiceException(ExceptionMessages.TEAM_NOT_EXIST);
        }
        return passwordManageTeamMapper.updatePasswordManageTeam(dto);
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
     * 解散团队
     * 
     * @param id 团队id
     * @return 结果
     */
    @Override
    public int deletePasswordManageTeamById(Long teamId) {
        if (passwordManageTeamMapper.selectPasswordManageTeamById(teamId) == null) {
            throw new ServiceException(ExceptionMessages.TEAM_NOT_EXIST);
        }
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
