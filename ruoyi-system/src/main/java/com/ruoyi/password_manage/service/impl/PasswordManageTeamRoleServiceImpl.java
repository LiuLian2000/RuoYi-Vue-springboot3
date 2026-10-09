package com.ruoyi.password_manage.service.impl;

import java.util.List;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.bean.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.ruoyi.password_manage.mapper.PasswordManageTeamMapper;
import com.ruoyi.password_manage.mapper.PasswordManageTeamRoleMapper;
import com.ruoyi.password_manage.mapper.PasswordManageUserMapper;
import com.ruoyi.password_manage.common.constants.ExceptionMessages;
import com.ruoyi.password_manage.common.constants.TeamRole;
import com.ruoyi.password_manage.domain.PasswordManageTeamRole;
import com.ruoyi.password_manage.domain.PasswordManageUser;
import com.ruoyi.password_manage.domain.dto.PasswordManageTeamRoleDto;
import com.ruoyi.password_manage.domain.vo.MyTeamVo;
import com.ruoyi.password_manage.domain.vo.TeamCandidateMemberVo;
import com.ruoyi.password_manage.domain.vo.TeamCreatorRoleVo;
import com.ruoyi.password_manage.domain.vo.TeamMemberVo;
import com.ruoyi.password_manage.service.IPasswordManageTeamRoleService;
import com.ruoyi.password_manage.service.PasswordManageUserService;

/**
 * 用户在团队中的角色Service业务层处理
 * 
 * @author DiZhicong
 * @date 2026-09-02
 */
@Service
@Transactional
public class PasswordManageTeamRoleServiceImpl implements IPasswordManageTeamRoleService {

    @Autowired
    private PasswordManageUserService passwordManageUserService;

    @Autowired
    private PasswordManageTeamRoleMapper passwordManageTeamRoleMapper;

    @Autowired
    private PasswordManageUserMapper passwordManageUserMapper;

    @Autowired
    private PasswordManageTeamMapper passwordManageTeamMapper;

    /**
     * 查询用户在团队中的角色
     * 
     * @param id 用户在团队中的角色主键
     * @return 用户在团队中的角色
     */
    @Override
    public PasswordManageTeamRole selectPasswordManageTeamRoleById(Long id) {
        return passwordManageTeamRoleMapper.selectPasswordManageTeamRoleById(id);
    }

    /**
     * 查询团队中的所有成员列表
     * 
     * @param id 团队id
     * @return 团队中的所有成员列表
     */
    @Override
    public List<TeamMemberVo> selectTeamMemberList(Long id) {
        return passwordManageTeamRoleMapper.selectTeamMemberList(id);
    }

    /**
     * 新增团队成员
     * 
     * @param passwordManageTeamRole 用户在团队中的成员
     * @return 结果
     */
    @Override
    public int insertPasswordManageTeamRole(PasswordManageTeamRole passwordManageTeamRole) {
        if (passwordManageTeamRoleMapper.selectTeamMemberHaveExist(passwordManageTeamRole.getTeamId(),
                passwordManageTeamRole.getUserId()) != 0) {
            throw new ServiceException(ExceptionMessages.TEAM_MEMBER_HAVE_EXIST);
        }
        return passwordManageTeamRoleMapper.insertPasswordManageTeamRole(passwordManageTeamRole);
    }

    /**
     * 删除团队中某成员
     * 
     * @param teamId 团队id
     * @param userId 用户id
     * @return 结果
     */
    @Override
    public int deletePasswordManageTeamRoleById(Long teamId, Long passwordManageUserId) {
        if (passwordManageTeamRoleMapper.selectTeamMemberHaveExist(teamId, passwordManageUserId) == 0) {
            throw new ServiceException(ExceptionMessages.TEAM_MEMBER_NOT_EXIST);
        }
        Long loginUserId = passwordManageUserService
                .selectPasswordManageUserByUserId(SecurityUtils.getLoginUser().getUserId()).getId();
        PasswordManageTeamRole role = selectPasswordManageTeamRoleByPasswordManageUserId(teamId, loginUserId);
        // 如果是管理员，不能删除管理员
        if (TeamRole.ADMIN == role.getTeamRole()) {
            List<Long> list = passwordManageTeamRoleMapper.selectTeamManagerPasswordManageUserIdList(teamId);
            if (list.contains(passwordManageUserId)) {
                throw new ServiceException(ExceptionMessages.CANNOT_DELETE_ADMIN);
            }
        }
        return passwordManageTeamRoleMapper.deletePasswordManageTeamRoleById(teamId, passwordManageUserId);
    }

    /**
     * 查询用户所在团队列表
     */
    @Override
    public List<MyTeamVo> selectPasswordManageTeamList(Long id) {
        PasswordManageUser tmp = passwordManageUserMapper.selectPasswordManageUserByUserId(id);
        return passwordManageTeamRoleMapper.selectPasswordManageTeamList(tmp.getId());
    }

    @Override
    public Integer selectTeamRoleOfMember(Long teamId, long userId) {
        return passwordManageTeamRoleMapper.selectTeamRoleOfMember(teamId, userId);
    }

    @Override
    public PasswordManageTeamRole selectPasswordManageTeamRoleByPasswordManageUserId(Long teamId,
            Long passwordManageUserId) {
        PasswordManageTeamRole userRoleInfo = passwordManageTeamRoleMapper
                .selectPasswordManageTeamRoleByPasswordManageUserId(teamId,
                        passwordManageUserId);
        if (userRoleInfo == null) {
            throw new RuntimeException(ExceptionMessages.NO_MEM_ROLE);
        }
        return userRoleInfo;
    }

    /**
     * (non-Javadoc)
     * 新增团队成员
     * 
     * @see com.ruoyi.password_manage.service.IPasswordManageTeamRoleService#addTeamMember(com.ruoyi.password_manage.domain.dto.PasswordManageTeamRoleDto,
     *      java.lang.Long)
     */
    @Override
    public TeamCreatorRoleVo addTeamMember(PasswordManageTeamRoleDto vo, Long teamManagerUserId) {
        Long memberId = vo.getUserId();
        Long teamId = vo.getTeamId();
        PasswordManageTeamRole passwordManageTeamRole = new PasswordManageTeamRole();
        BeanUtils.copyProperties(vo, passwordManageTeamRole);
        passwordManageTeamRole.setTeamId(teamId);
        passwordManageTeamRole.setUserId(memberId);
        passwordManageTeamRole.setTeamRole(TeamRole.MEMBER);
        // 尚未使用被添加成员公钥对金库秘钥加密
        if (StringUtils.isEmpty(passwordManageTeamRole.getTeamValutKeyEncryptedCipher())) {
            // 建立该成员的role记录
            passwordManageTeamRoleMapper.insertPasswordManageTeamRole(passwordManageTeamRole);
            // 将当前管理员的role记录和被添加团队成员的公钥传回前端
            TeamCreatorRoleVo teamCreatorRoleVo = new TeamCreatorRoleVo();
            PasswordManageUser member = passwordManageUserMapper.selectPasswordManageUserById(memberId);
            teamCreatorRoleVo.setMemberPublicKey(member.getPublicKey());
            PasswordManageTeamRole teamCreatorRole = passwordManageTeamRoleMapper
                    .selectPasswordManageTeamRoleByPasswordManageUserId(teamId, teamManagerUserId);
            teamCreatorRoleVo.setTeamValutKeyEncryptedCipher(teamCreatorRole.getTeamValutKeyEncryptedCipher());
            teamCreatorRoleVo.setNewMemberRoleId(passwordManageTeamRole.getId());
            return teamCreatorRoleVo;
        } else {
            // 被添加成员的被加密金库秘钥由前端传回（此次调用需要传入新成员在role表中记录的id）
            passwordManageTeamRoleMapper.updatePasswordManageTeamRole(passwordManageTeamRole);
            return null;
        }

    }

    /**
     * (non-Javadoc)
     * 成员退出团队
     * 
     * @see com.ruoyi.password_manage.service.IPasswordManageTeamRoleService#MemberLeaveTeam(java.lang.Long,
     *      java.lang.Long)
     */
    @Override
    public Integer MemberLeaveTeam(Long teamId, Long passwordManageUserId) {
        PasswordManageTeamRole role = passwordManageTeamRoleMapper
                .selectPasswordManageTeamRoleByPasswordManageUserId(teamId, passwordManageUserId);
        if (role == null) {
            throw new RuntimeException(ExceptionMessages.NOT_TEAM_MEM);
        } else if (TeamRole.SUPER_ADMIN == role.getTeamRole()) {
            throw new RuntimeException(ExceptionMessages.SUPER_ADMIN_CANNOT_LEAVE_TEAM);
        }
        return passwordManageTeamRoleMapper.MemberLeaveTeam(teamId, passwordManageUserId);
    }

    /**
     * (non-Javadoc)
     * 获取团队可添加成员列表
     * 
     * @see com.ruoyi.password_manage.service.IPasswordManageTeamRoleService#selectCandidateMembers(java.lang.Long)
     */
    @Override
    public List<TeamCandidateMemberVo> selectCandidateMembers(Long teamId) {
        List<TeamCandidateMemberVo> list = passwordManageTeamRoleMapper.selectCandidateMembers(teamId);
        // 过滤掉admin用户
        for (TeamCandidateMemberVo vo : list) {
            if ("admin".equals(vo.getUserName())) {
                list.remove(vo);
                break;
            }
        }
        return list;
    }

    /**
     * (non-Javadoc)
     * 设置团队管理员
     * 
     * @see com.ruoyi.password_manage.service.IPasswordManageTeamRoleService#setTeamManagerAuth(java.lang.Long,
     *      java.lang.Long)
     */
    @Override
    public Integer setTeamManagerAuth(Long teamId, Long passwordManageUserId) {
        Integer role = TeamRole.ADMIN;
        return passwordManageTeamRoleMapper.updateTeamMemberRole(teamId, passwordManageUserId, role);
    }

    /**
     * (non-Javadoc)
     * 移除团队管理员
     * 
     * @see com.ruoyi.password_manage.service.IPasswordManageTeamRoleService#removeTeamManagerAuth(java.lang.Long,
     *      java.lang.Long)
     */
    @Override
    public Integer removeTeamManagerAuth(Long teamId, Long PasswordManageUserId) {
        Integer role = TeamRole.MEMBER;
        return passwordManageTeamRoleMapper.updateTeamMemberRole(teamId, PasswordManageUserId, role);
    }

    /**
     * (non-Javadoc)
     * 移交团队超级管理员
     * 
     * @see com.ruoyi.password_manage.service.IPasswordManageTeamRoleService#transTeamSuperManagerAuth(java.lang.Long,
     *      java.lang.Long, java.lang.Long)
     */
    @Override
    public Integer transTeamSuperManagerAuth(Long teamId, Long passwordManageUserId, Long nowSuperAdminUserId) {
        Integer role = TeamRole.SUPER_ADMIN;
        Integer row1 = passwordManageTeamRoleMapper.updateTeamMemberRole(teamId, passwordManageUserId,
                role);
        role = TeamRole.ADMIN;
        Integer row2 = passwordManageTeamRoleMapper.updateTeamMemberRole(teamId, nowSuperAdminUserId, role);
        Integer row3 = passwordManageTeamMapper.updateTeamSuperAdmin(teamId, passwordManageUserId);
        return (row1 + row2 + row3);
    }

}
