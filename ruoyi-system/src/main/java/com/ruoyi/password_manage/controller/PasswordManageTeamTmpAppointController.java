package com.ruoyi.password_manage.controller;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.password_manage.domain.PasswordManageTeamTmpAppoint;
import com.ruoyi.password_manage.domain.dto.PasswordManageTeamTmpAppointDto;
import com.ruoyi.password_manage.service.IPasswordManageTeamTmpAppointService;

@RestController
@RequestMapping("/password_manage/team_appoint")
@Transactional
public class PasswordManageTeamTmpAppointController extends BaseController {

    @Autowired
    private IPasswordManageTeamTmpAppointService passwordManageTeamTmpAppointService;

    @PostMapping
    public AjaxResult addTmpAppoint(PasswordManageTeamTmpAppointDto dto) {
        PasswordManageTeamTmpAppoint tmpAppoint = new PasswordManageTeamTmpAppoint();
        BeanUtils.copyProperties(dto, tmpAppoint);
        passwordManageTeamTmpAppointService.addPasswordManageTeamTmpAppoint(tmpAppoint);
        return success();
    }

    @GetMapping
    public AjaxResult fetchTmpAppoint() {
        Long sysusrId = getLoginUser().getUserId();
        passwordManageTeamTmpAppointService.selecTeamTmpAppointBySysusrId(sysusrId);
        return success();
    }
}
