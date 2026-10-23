package com.ruoyi.password_manage.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import com.ruoyi.password_manage.domain.PasswordManageTeamApply;
import com.ruoyi.password_manage.domain.vo.TeamApplyVo;

/**
 * 团队入队申请Mapper接口
 * 
 * @author DiZhicong
 * @date 2026-10-23
 */
public interface PasswordManageTeamApplyMapper {

    /**
     * 新增团队入队申请
     * 
     * @param passwordManageTeamApply 申请记录
     * @return 结果
     */
    public int insertPasswordManageTeamApply(PasswordManageTeamApply passwordManageTeamApply);

    /**
     * 查询申请记录
     * 
     * @param id 主键
     * @return 申请记录
     */
    public PasswordManageTeamApply selectPasswordManageTeamApplyById(Long id);

    /**
     * 查询我的申请列表
     * 
     * @param applicantPmUserId 申请人 password_manage_user.id
     * @return 申请列表
     */
    public List<TeamApplyVo> selectMyApplyList(@Param("applicantPmUserId") Long applicantPmUserId);

    /**
     * 查询某团队的待审申请列表
     * 
     * @param teamId 团队id
     * @return 待审申请列表
     */
    public List<TeamApplyVo> selectPendingApplyList(@Param("teamId") Long teamId);

    /**
     * 更新申请状态/审批信息
     * 
     * @param passwordManageTeamApply 申请记录
     * @return 结果
     */
    public int updatePasswordManageTeamApply(PasswordManageTeamApply passwordManageTeamApply);

    /**
     * 统计某人在某团队的待审申请数量（用于防重复申请）
     * 
     * @param teamId 团队id
     * @param applicantPmUserId 申请人 password_manage_user.id
     * @return 数量
     */
    public int countPendingApply(@Param("teamId") Long teamId,
            @Param("applicantPmUserId") Long applicantPmUserId);
}
