package com.ruoyi.password_manage.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ruoyi.password_manage.domain.PasswordManageTeamTmpAppoint;
import com.ruoyi.password_manage.mapper.PasswordManageTeamTmpAppointMapper;
import com.ruoyi.password_manage.service.IPasswordManageTeamTmpAppointService;

@Service
@Transactional
public class PasswordManageTeamTmpAppointImpl implements IPasswordManageTeamTmpAppointService {

    @Autowired
    private PasswordManageTeamTmpAppointMapper passwordManageTeamTmpAppointMapper;

    @Override
    public void addPasswordManageTeamTmpAppoint(PasswordManageTeamTmpAppoint passwordManageTeamTmpAppoint) {
        passwordManageTeamTmpAppointMapper.insertPasswordManageTeamTmpAppoint(passwordManageTeamTmpAppoint);
    }

    @Override
    public PasswordManageTeamTmpAppoint selecTeamTmpAppointBySysusrId(Long sysusrId) {
        return passwordManageTeamTmpAppointMapper.selecTeamTmpAppointBySysusrId(sysusrId);
    }

}
