package com.ruoyi.password_manage.common.constants;

public class ExceptionMessages {

    /**
     * 任何非业务异常信息外，controller统一返回此信息
     */
    public static final String NORMAL = "当前数据操作异常，请联系管理员。";

    public static final String USER_NO_PER_ACCESS = "无权访问该凭据。";

    public static final String VAULT_UNLOCK = "当前用户尚未初始化金库，无法执行此操作，请先解锁用户金库。";

    public static final String TEAM_NAME_HAVE_EXIST = "当前团队名称已存在，请更改团队名称。";

    public static final String TEAM_NOT_EXIST = "该团队不存在，请检查操作团队是否正确。";

    public static final String NO_MEM_ROLE = "非团队成员，未查询到团队成员的角色信息。";

    public static final String NOT_TEAM_MEM = "非团队成员，无团队操作权限。";

    public static final String NOT_TEAM_ADMIN = "非团队管理员，无团队管理员操作权限。";

    public static final String NOT_TEAM_SUPER_ADMIN = "非团队超级管理员，无团队超级管理员操作权限。";

    public static final String SUPER_ADMIN_CANNOT_LEAVE_TEAM = "团队超级管理员无法退出团队，请先转让团队超级管理员权限。";

    public static final String TEAM_MEMBER_HAVE_EXIST = "该成员在团队中已存在，无法重复添加。";

    public static final String TEAM_MEMBER_NOT_EXIST = "该成员在团队中不存在，无法删除。";

    public static final String CANNOT_DELETE_ADMIN = "无法对团队管理员执行删除操作。";

}
