package com.ruoyi.password_manage.mapper;

import java.util.List;
import com.ruoyi.password_manage.domain.PasswordManageTeam;
import com.ruoyi.password_manage.domain.dto.EditPasswordManageTeamDto;
import com.ruoyi.password_manage.domain.vo.MyManagementTeamVo;

/**
 * 团队密码管理-团队密码Mapper接口
 * 
 * @author DiZhicong
 * @date 2026-09-01
 */
public interface PasswordManageTeamMapper {
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
     * @param id admin id
     * @return 管理团队信息集合
     */
    public List<MyManagementTeamVo> selectManageTeamList(Long id);

    /**
     * 新增团队密码管理-团队密码
     * 
     * @param passwordManageTeam 团队密码管理-团队密码
     * @return 结果
     */
    public int insertPasswordManageTeam(PasswordManageTeam passwordManageTeam);

    /**
     * 修改团队信息
     * 
     * @param passwordManageTeam 团队密码管理-团队密码
     * @return 结果
     */
    public int updatePasswordManageTeam(EditPasswordManageTeamDto dto);

    /**
     * 删除团队密码管理-团队密码
     * 
     * @param id 团队密码管理-团队密码主键
     * @return 结果
     */
    public int deletePasswordManageTeamById(Long id);

    /**
     * 批量删除团队密码管理-团队密码
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deletePasswordManageTeamByIds(Long[] ids);

    /**
     * 查询以成员身份所在的团队列表
     * 
     * @param passwordManageUserId
     * @return
     */
    public List<PasswordManageTeam> selectTeamList(Long passwordManageUserId);

    /**
     * 更新该团队的超级管理员
     * 
     * @param teamId
     * @param passwordManageUserId
     * @return
     */
    public Integer updateTeamSuperAdmin(Long teamId, Long passwordManageUserId);

    /**
     * 根据团队名称判断该团队是否已经存在
     * 
     * @param teamName
     * @return
     */
    public Integer selectTeamByTeamName(String teamName);
}
