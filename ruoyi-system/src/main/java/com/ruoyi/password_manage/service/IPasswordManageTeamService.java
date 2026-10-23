package com.ruoyi.password_manage.service;

import java.util.List;
import com.ruoyi.password_manage.domain.PasswordManageTeam;
import com.ruoyi.password_manage.domain.dto.EditPasswordManageTeamDto;
import com.ruoyi.password_manage.domain.vo.MyManagementTeamVo;
import com.ruoyi.password_manage.domain.vo.PasswordManageTeamVo;
import com.ruoyi.password_manage.domain.vo.TeamSearchVo;

/**
 * 团队密码管理-团队密码Service接口
 * 
 * @author DiZhicong
 * @date 2026-09-01
 */
public interface IPasswordManageTeamService {
    /**
     * 查询团队密码管理-团队密码
     * 
     * @param id 团队密码管理-团队密码主键
     * @return 团队密码管理-团队密码
     */
    public PasswordManageTeam selectPasswordManageTeamById(Long id);

    /**
     * 查询管理团队列表
     * 
     * @param id 用户id
     * @return 管理团队信息集合
     */
    public List<MyManagementTeamVo> selectManageTeamList(Long id);

    /**
     * 新增团队密码管理-团队密码
     * 
     * @param passwordManageTeam 团队密码管理-团队密码
     * @return 结果
     */
    public Long insertPasswordManageTeam(PasswordManageTeamVo vo);

    /**
     * 修改团队信息
     *
     * @param passwordManageTeam 团队密码管理-团队密码
     * @return 结果
     */
    public int updatePasswordManageTeam(EditPasswordManageTeamDto dto);

    /**
     * 批量删除团队密码管理-团队密码
     * 
     * @param ids 需要删除的团队密码管理-团队密码主键集合
     * @return 结果
     */
    public int deletePasswordManageTeamByIds(Long[] ids);

    /**
     * 删除团队
     * 
     * @param id 团队密码管理-团队密码主键
     * @return 结果
     */
    public int deletePasswordManageTeamById(Long teamId);

    /**
     * 查询以成员身份所在的团队列表
     * 
     * @param passwordManageUserId
     * @return
     */
    public List<PasswordManageTeam> selectTeamList(Long passwordManageUserId);

    /**
     * 查询团队管理员的Password_manage_user_id
     */
    public List<Long> selectTeamManagerPasswordManageUserIdList(Long teamId);

    /**
     * 搜索团队（按名称模糊），并标记当前用户的加入/申请状态
     * 
     * @param keyword 关键字
     * @param currentPmUserId 当前用户 password_manage_user.id
     * @return 团队搜索结果
     */
    public List<TeamSearchVo> searchTeams(String keyword, Long currentPmUserId);
}
