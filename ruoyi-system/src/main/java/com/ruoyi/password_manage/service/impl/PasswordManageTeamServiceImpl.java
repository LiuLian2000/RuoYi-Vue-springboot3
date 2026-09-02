package com.ruoyi.password_manage.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.password_manage.mapper.PasswordManageTeamMapper;
import com.ruoyi.password_manage.mapper.PasswordManageTeamRoleMapper;
import com.ruoyi.password_manage.domain.PasswordManageTeam;
import com.ruoyi.password_manage.service.IPasswordManageTeamService;

/**
 * 团队密码管理-团队密码Service业务层处理
 * 
 * @author DiZhicong
 * @date 2026-09-01
 */
@Service
public class PasswordManageTeamServiceImpl implements IPasswordManageTeamService {
    @Autowired
    private PasswordManageTeamMapper passwordManageTeamMapper;

    @Autowired
    private PasswordManageTeamRoleMapper passwordManageTeamRoleMapper;

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
        passwordManageTeam.setCreateTime(DateUtils.getNowDate());
        return passwordManageTeamMapper.insertPasswordManageTeam(passwordManageTeam);
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
