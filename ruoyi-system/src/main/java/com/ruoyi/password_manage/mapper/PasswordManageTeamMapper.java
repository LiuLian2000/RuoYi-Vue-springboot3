package com.ruoyi.password_manage.mapper;

import java.util.List;
import com.ruoyi.password_manage.domain.PasswordManageTeam;

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
    public List<PasswordManageTeam> selectPasswordManageTeamList(Long id);

    /**
     * 新增团队密码管理-团队密码
     * 
     * @param passwordManageTeam 团队密码管理-团队密码
     * @return 结果
     */
    public int insertPasswordManageTeam(PasswordManageTeam passwordManageTeam);

    /**
     * 修改团队密码管理-团队密码
     * 
     * @param passwordManageTeam 团队密码管理-团队密码
     * @return 结果
     */
    public int updatePasswordManageTeam(PasswordManageTeam passwordManageTeam);

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
}
