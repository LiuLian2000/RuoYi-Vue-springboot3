package com.ruoyi.password_manage.mapper;

import com.ruoyi.password_manage.domain.PasswordManageTeamTmpAppoint;

public interface PasswordManageTeamTmpAppointMapper {

    void insertPasswordManageTeamTmpAppoint(PasswordManageTeamTmpAppoint tmpAppoint);

    public PasswordManageTeamTmpAppoint selecTeamTmpAppointBySysusrId(Long sysusrId);
}