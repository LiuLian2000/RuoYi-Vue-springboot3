package com.ruoyi.password_manage.service.impl;

import java.util.List;

import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.bean.BeanUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.ruoyi.password_manage.mapper.PasswordManageTeamRoleMapper;
import com.ruoyi.password_manage.mapper.PasswordManageUserMapper;
import com.ruoyi.password_manage.domain.PasswordManageTeam;
import com.ruoyi.password_manage.domain.PasswordManageTeamRole;
import com.ruoyi.password_manage.domain.PasswordManageUser;
import com.ruoyi.password_manage.domain.vo.PasswordManageMemberVo;
import com.ruoyi.password_manage.domain.vo.PasswordManageTeamRoleVo;
import com.ruoyi.password_manage.domain.vo.TeamCreatorRoleVo;
import com.ruoyi.password_manage.service.IPasswordManageTeamRoleService;

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
    private PasswordManageTeamRoleMapper passwordManageTeamRoleMapper;

    @Autowired
    private PasswordManageUserMapper passwordManageUserMapper;

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
    public List<SysUser> selectPasswordManageTeamRoleList(Long id) {
        return passwordManageTeamRoleMapper.selectPasswordManageTeamRoleList(id);
    }

    @Override
    public List<PasswordManageMemberVo> selectCandidateMembers(Long teamId) {
        return passwordManageTeamRoleMapper.selectCandidateMembers(teamId);
    }

    /**
     * 新增用户在团队中的角色
     * 
     * @param passwordManageTeamRole 用户在团队中的角色
     * @return 结果
     */
    @Override
    public int insertPasswordManageTeamRole(PasswordManageTeamRole passwordManageTeamRole) {
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
    public int updatePasswordManageTeamRole(PasswordManageTeamRole passwordManageTeamRole) {
        passwordManageTeamRole.setUpdateTime(DateUtils.getNowDate());
        return passwordManageTeamRoleMapper.updatePasswordManageTeamRole(passwordManageTeamRole);
    }

    // /**
    // * 批量删除用户在团队中的角色
    // *
    // * @param ids 需要删除的用户在团队中的角色主键
    // * @return 结果
    // */
    // @Override
    // public int deletePasswordManageTeamRoleByIds(Long[] ids) {
    // return passwordManageTeamRoleMapper.deletePasswordManageTeamRoleByIds(ids);
    // }

    /**
     * 删除团队中某用户
     * 
     * @param teamId 团队id
     * @param userId 用户id
     * @return 结果
     */
    @Override
    public int deletePasswordManageTeamRoleById(Long teamId, Long userId) {
        return passwordManageTeamRoleMapper.deletePasswordManageTeamRoleById(teamId, userId);
    }

    /**
     * 获取所属团队列表
     */
    @Override
    public List<PasswordManageTeam> selectPasswordManageTeamList(Long id) {
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
            throw new RuntimeException("非团队成员，未查询到团队成员的角色信息。");
        }
        return userRoleInfo;
    }

    @Override
    public TeamCreatorRoleVo addTeamMember(PasswordManageTeamRoleVo vo, Long teamCreatorPasswordManageUserId) {
        Long memberId = vo.getUserId();
        Long teamId = vo.getOperatedTeamId();
        PasswordManageTeamRole passwordManageTeamRole = new PasswordManageTeamRole();
        BeanUtils.copyProperties(vo, passwordManageTeamRole);
        passwordManageTeamRole.setTeamId(teamId);
        passwordManageTeamRole.setUserId(memberId);
        passwordManageTeamRole.setTeamRole(1);
        // 尚未使用被添加成员公钥对金库秘钥加密
        if (StringUtils.isEmpty(passwordManageTeamRole.getTeamValutKeyEncryptedCipher())) {
            // 建立该成员的role记录
            passwordManageTeamRoleMapper.insertPasswordManageTeamRole(passwordManageTeamRole);
            // 将管理员的role记录和被添加团队成员的公钥传回前端
            TeamCreatorRoleVo teamCreatorRoleVo = new TeamCreatorRoleVo();
            PasswordManageUser member = passwordManageUserMapper.selectPasswordManageUserById(memberId);
            teamCreatorRoleVo.setMemberPublicKey(member.getPublicKey());
            PasswordManageTeamRole teamCreatorRole = passwordManageTeamRoleMapper
                    .selectPasswordManageTeamRoleByPasswordManageUserId(teamId, teamCreatorPasswordManageUserId);
            teamCreatorRoleVo.setRandomSalt(teamCreatorRole.getRandomSalt());
            teamCreatorRoleVo.setRandomIv(teamCreatorRole.getRandomIv());
            teamCreatorRoleVo.setTeamValutKeyEncryptedCipher(teamCreatorRole.getTeamValutKeyEncryptedCipher());
            return teamCreatorRoleVo;
        } else {
            // 被添加成员的被加密金库秘钥已传回
            passwordManageTeamRoleMapper.updatePasswordManageTeamRole(passwordManageTeamRole);
            return null;
        }

    }

    /**
     * 团队成员退出团队
     */
    public Integer MemberLeaveTeam(Long teamId, Long passwordManageUserId) {
        PasswordManageTeamRole role = passwordManageTeamRoleMapper
                .selectPasswordManageTeamRoleByPasswordManageUserId(teamId, passwordManageUserId);
        if (role == null || 1 != role.getIsDeleted()) {
            throw new RuntimeException("非团队成员，无团队操作权限。");
        } else if (0 == role.getTeamRole()) {
            throw new RuntimeException("团队管理员无法退出团队，请先转让团队管理员权限。");
        }
        return passwordManageTeamRoleMapper.MemberLeaveTeam(teamId, passwordManageUserId);
    }

}
