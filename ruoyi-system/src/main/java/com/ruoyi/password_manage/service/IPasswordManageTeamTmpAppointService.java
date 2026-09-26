package com.ruoyi.password_manage.service;

import com.ruoyi.password_manage.domain.PasswordManageTeamTmpAppoint;

public interface IPasswordManageTeamTmpAppointService {

    void addPasswordManageTeamTmpAppoint(PasswordManageTeamTmpAppoint passwordManageTeamTmpAppoint);

    PasswordManageTeamTmpAppoint selecTeamTmpAppointBySysusrId(Long sysusrId);
}
