package com.ruoyi.password_manage.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ruoyi.password_manage.mapper.PasswordManageTeamCredentialMapper;
import com.ruoyi.password_manage.domain.PasswordManageTeamCredential;
import com.ruoyi.password_manage.service.IPasswordManageTeamCredentialService;

/**
 * 团队密码凭据增删改查Service业务层处理
 * 
 * @author DiZhicong
 * @date 2026-09-02
 */
@Service
@Transactional
public class PasswordManageTeamCredentialServiceImpl implements IPasswordManageTeamCredentialService {

    @Autowired
    private PasswordManageTeamCredentialMapper passwordManageTeamCredentialMapper;

    /**
     * 查询团队密码凭据增删改查
     * 
     * @param id 团队密码凭据增删改查主键
     * @return 团队密码凭据增删改查
     */
    @Override
    public PasswordManageTeamCredential selectPasswordManageTeamCredentialById(Long id) {
        return passwordManageTeamCredentialMapper.selectPasswordManageTeamCredentialById(id);
    }

    /**
     * 查询团队密码凭据
     * 
     * @param passwordManageTeamCredential 团队密码凭据
     * @return 团队密码凭据
     */
    @Override
    public List<PasswordManageTeamCredential> selectPasswordManageTeamCredentialList(Long teamId) {
        return passwordManageTeamCredentialMapper.selectPasswordManageTeamCredentialList(teamId);
    }

    /**
     * 新增团队密码凭据增删改查
     * 
     * @param passwordManageTeamCredential 团队密码凭据增删改查
     * @return 结果
     */
    @Override
    public int insertPasswordManageTeamCredential(PasswordManageTeamCredential passwordManageTeamCredential) {
        passwordManageTeamCredential.setCreateTime(DateUtils.getNowDate());
        return passwordManageTeamCredentialMapper.insertPasswordManageTeamCredential(passwordManageTeamCredential);
    }

    /**
     * 修改团队密码凭据增删改查
     * 
     * @param passwordManageTeamCredential 团队密码凭据增删改查
     * @return 结果
     */
    @Override
    public int updatePasswordManageTeamCredential(PasswordManageTeamCredential passwordManageTeamCredential) {
        return passwordManageTeamCredentialMapper.updatePasswordManageTeamCredential(passwordManageTeamCredential);
    }

    /**
     * 批量删除团队密码凭据增删改查
     * 
     * @param ids 需要删除的团队密码凭据增删改查主键
     * @return 结果
     */
    @Override
    public int deletePasswordManageTeamCredentialByIds(Long[] ids) {
        return passwordManageTeamCredentialMapper.deletePasswordManageTeamCredentialByIds(ids);
    }

    /**
     * 删除团队密码凭据增删改查信息
     * 
     * @param id 团队密码凭据增删改查主键
     * @return 结果
     */
    @Override
    public int deletePasswordManageTeamCredentialById(Long id) {
        return passwordManageTeamCredentialMapper.deletePasswordManageTeamCredentialById(id);
    }

    /**
     * 模糊查询团队密码凭据
     */
    @Override
    public List<PasswordManageTeamCredential> selectPasswordManageTeamCredential(Long teamId, String platName) {
        return passwordManageTeamCredentialMapper.selectPasswordManageTeamCredential(teamId, platName);
    }
}
