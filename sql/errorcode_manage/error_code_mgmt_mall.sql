-- ============================================================
-- 错误码管理系统建表脚本
-- 数据库: mall
-- 目标: 管理错误码所属系统、所属类别、严重程度，以及修改记录
-- 修改记录采用字段级明细: 主表(操作头) + 明细表(每个被改字段一行)
-- 执行: mysql -uroot -proot < mall.sql
--
-- ============================================================
-- ★ 外键策略: 本脚本不建任何数据库外键约束,
--   引用完整性全部由【后端业务逻辑】保证。
--   每张表下都用 "-- [后端处理]" 注释标明了对应关系需要哪些后端逻辑。
-- ============================================================

SET NAMES utf8mb4;

-- 逻辑删除约定：有效数据 del_flag=0；删除时将 del_flag 更新为本行 id（SET del_flag=id）。
-- 唯一索引包含 del_flag，删除后的历史数据不会阻止相同业务键再次创建。

USE ry-vue;

-- ------------------------------------------------------------
-- 删除旧表(顺序:先删子表,再删父表)
-- ------------------------------------------------------------
DROP TABLE IF EXISTS error_code_log_detail;
DROP TABLE IF EXISTS error_code_log;
DROP TABLE IF EXISTS error_code;
DROP TABLE IF EXISTS error_severity;
DROP TABLE IF EXISTS error_category;
DROP TABLE IF EXISTS error_system;

-- ------------------------------------------------------------
-- 1. 系统表: 错误码属于哪个系统
-- ------------------------------------------------------------
CREATE TABLE error_system (
    id          BIGINT UNSIGNED AUTO_INCREMENT COMMENT '主键ID',
    system_code VARCHAR(64)  NOT NULL COMMENT '系统编码',
    system_name VARCHAR(128) NOT NULL COMMENT '系统名称',
    description VARCHAR(512)          COMMENT '系统描述',
    status      TINYINT      NOT NULL DEFAULT 1 COMMENT '状态:1启用 0停用',
    version     INT UNSIGNED NOT NULL DEFAULT 0 COMMENT '乐观锁版本号',
    del_flag    BIGINT UNSIGNED NOT NULL DEFAULT 0 COMMENT '删除标志（0代表存在，删除时设置为本行ID）',
    create_by   VARCHAR(64)  DEFAULT NULL COMMENT '创建者',
    create_time DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_by   VARCHAR(64)  DEFAULT NULL COMMENT '更新者',
    update_time DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (id),
    UNIQUE KEY uk_system_code (system_code, del_flag)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='错误码所属系统表';

-- ------------------------------------------------------------
-- 2. 类别表: 错误码属于哪个类别(类别挂靠在系统下)
--    [后端处理] error_category.system_id -> error_system.id
--      新增/修改类别: 校验 system_id 对应的系统存在且启用
--      删除系统    : 先校验该系统下无类别, 否则拒绝或级联处理
-- ------------------------------------------------------------
-- TODO: 两个已有的错误码，需要交换内容，不能改其中的错误码是多少，会同时出现两个相同错误码，
-- 需要让只能修改错误码的描述啥的，不能直接改某个错误码是多少
-- TODO: 逻辑删除唯一索引要改
-- ① deleted = 自己的 id 技巧（改 schema + 一行逻辑）

-- 上轮方案 B（(system_id, code, deleted)）怕撞，是因为 deleted 只存 0/1。改一下存法：活的行 deleted=0，软删时 deleted 存成这行自己的主键 id。


-- UNIQUE KEY uk_system_code (system_id, code, deleted)
-- -- 软删时: UPDATE error_code SET deleted = id WHERE id = ?
-- 唯一性分析：

-- 活的：(system_id, code, 0) 至多一条 → 唯一约束兜底 ✓
-- 已删的：(system_id, code, <各自id>) 每条都不一样 → 任意次软删、任意次复用都不撞 ✓
-- 这是能扛住"建→删→建→删"循环的稳妥版，代价是 deleted 列语义变成了"0 或自己的 id"，不够直观，且软删语句必须是 SET deleted = id。

CREATE TABLE error_category (
    id            BIGINT UNSIGNED AUTO_INCREMENT COMMENT '主键ID',
    system_id     BIGINT UNSIGNED NOT NULL COMMENT '所属系统ID',
    category_code VARCHAR(64)  NOT NULL COMMENT '类别编码',
    category_name VARCHAR(128) NOT NULL COMMENT '类别名称',
    description   VARCHAR(512)          COMMENT '类别描述',
    status        TINYINT      NOT NULL DEFAULT 1 COMMENT '状态:1启用 0停用',
    version       INT UNSIGNED NOT NULL DEFAULT 0 COMMENT '乐观锁版本号',
    del_flag      BIGINT UNSIGNED NOT NULL DEFAULT 0 COMMENT '删除标志（0代表存在，删除时设置为本行ID）',
    create_by     VARCHAR(64)  DEFAULT NULL COMMENT '创建者',
    create_time   DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_by     VARCHAR(64)  DEFAULT NULL COMMENT '更新者',
    update_time   DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (id),
    UNIQUE KEY uk_system_category (system_id, category_code, del_flag)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='错误码类别表';

-- ------------------------------------------------------------
-- 3. 严重程度表: 错误码的严重程度字典
--    [后端处理] error_code.severity_id -> error_severity.id
--      新增/修改错误码: 校验 severity_id 对应记录存在且启用
--      删除/停用严重程度: 先校验无错误码引用
-- ------------------------------------------------------------
CREATE TABLE error_severity (
    id            BIGINT UNSIGNED AUTO_INCREMENT COMMENT '主键ID',
    severity_code TINYINT      NOT NULL COMMENT '严重程度代码:1提示 2警告 3错误 4致命',
    severity_name VARCHAR(32)  NOT NULL COMMENT '严重程度名称',
    description   VARCHAR(255)          COMMENT '描述',
    status        TINYINT      NOT NULL DEFAULT 1 COMMENT '状态:1启用 0停用',
    version       INT UNSIGNED NOT NULL DEFAULT 0 COMMENT '乐观锁版本号',
    del_flag      BIGINT UNSIGNED NOT NULL DEFAULT 0 COMMENT '删除标志（0代表存在，删除时设置为本行ID）',
    create_by     VARCHAR(64)  DEFAULT NULL COMMENT '创建者',
    create_time   DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_by     VARCHAR(64)  DEFAULT NULL COMMENT '更新者',
    update_time   DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (id),
    UNIQUE KEY uk_severity_code (severity_code, del_flag)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='错误码严重程度表';

-- ------------------------------------------------------------
-- 4. 错误码表: 同时挂系统 + 类别 + 严重程度
--    [后端处理] error_code.system_id   -> error_system.id
--      新增/修改错误码: 校验 system_id 存在且启用
--      删除系统       : 先校验该系统下无错误码
--    [后端处理] error_code.category_id -> error_category.id
--      新增/修改错误码: 校验 category_id 存在
--      ★ 重点: 原复合外键(类别必须属于该系统)已移除,
--         后端必须校验: 所填 category 的 system_id == 错误码的 system_id,
--         否则会出现"前台的类别挂到后台的错误码上"这类脏数据
--    [后端处理] error_code.severity_id -> error_severity.id
--      新增/修改错误码: 校验 severity_id 对应记录存在且启用
--      删除类别       : 先校验该类别下无错误码
-- ------------------------------------------------------------
CREATE TABLE error_code (
    id          BIGINT UNSIGNED AUTO_INCREMENT COMMENT '主键ID',
    system_id   BIGINT UNSIGNED NOT NULL COMMENT '所属系统ID',
    category_id BIGINT UNSIGNED NOT NULL COMMENT '所属类别ID',
    code        VARCHAR(32)  NOT NULL COMMENT '错误码(如11001)',
    message     VARCHAR(255) NOT NULL COMMENT '错误提示信息',
    description VARCHAR(512)          COMMENT '错误详细说明',
    severity_id BIGINT UNSIGNED NOT NULL COMMENT '严重程度ID',
    status      TINYINT      NOT NULL DEFAULT 1 COMMENT '状态:1启用 0停用',
    version     INT UNSIGNED NOT NULL DEFAULT 0 COMMENT '乐观锁版本号',
    del_flag    BIGINT UNSIGNED NOT NULL DEFAULT 0 COMMENT '删除标志（0代表存在，删除时设置为本行ID）',
    create_by   VARCHAR(64)  DEFAULT NULL COMMENT '创建者',
    create_time DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_by   VARCHAR(64)  DEFAULT NULL COMMENT '更新者',
    update_time DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (id),
    UNIQUE KEY uk_system_code (system_id, code, del_flag),
    KEY idx_category_id (category_id),
    KEY idx_severity_id (severity_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='错误码表';

-- ------------------------------------------------------------
-- 5. 修改记录主表: 一次修改对应一条记录(操作头信息)
--    [后端处理] error_code_log.error_code_id -> error_code.id
--      写日志时: 校验 error_code_id 存在
--      ★ 删除错误码时: 必须把该错误码相关日志的 error_code_id 置 NULL,
--        日志行保留(模拟原 ON DELETE SET NULL), 不可级联删除日志
--      查询日志时: error_code_id 为 NULL 表示"该错误码已被删除"
-- ------------------------------------------------------------
CREATE TABLE error_code_log (
    id            BIGINT UNSIGNED AUTO_INCREMENT COMMENT '主键ID',
    error_code_id BIGINT UNSIGNED NULL COMMENT '错误码ID(删除错误码后置NULL,日志保留)',
    operation     VARCHAR(16)  NOT NULL COMMENT '操作类型:CREATE/UPDATE/DELETE',
    operator      VARCHAR(64)  COMMENT '操作人',
    remark        VARCHAR(512) COMMENT '变更备注',
    del_flag      BIGINT UNSIGNED NOT NULL DEFAULT 0 COMMENT '删除标志（0代表存在，删除时设置为本行ID）',
    create_time   DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '记录创建时间',
    PRIMARY KEY (id),
    KEY idx_log_error_code (error_code_id),
    KEY idx_log_operation (operation),
    KEY idx_log_create_time (create_time)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='错误码修改记录主表';

-- ------------------------------------------------------------
-- 6. 修改记录明细表: 每个被修改的字段一行(字段级 diff)
--    CREATE 时 old_value 为 NULL, DELETE 时 new_value 为 NULL
--    [后端处理] error_code_log_detail.log_id -> error_code_log.id
--      写明细时: 校验 log_id 存在
--      删除日志时: 必须级联删除其明细(模拟原 ON DELETE CASCADE)
-- ------------------------------------------------------------
CREATE TABLE error_code_log_detail (
    id         BIGINT UNSIGNED AUTO_INCREMENT COMMENT '主键ID',
    log_id     BIGINT UNSIGNED NOT NULL COMMENT '修改记录主表ID',
    field_name VARCHAR(64)   NOT NULL COMMENT '被修改字段名',
    old_value  VARCHAR(1024) COMMENT '修改前值',
    new_value  VARCHAR(1024) COMMENT '修改后值',
    del_flag   BIGINT UNSIGNED NOT NULL DEFAULT 0 COMMENT '删除标志（0代表存在，删除时设置为本行ID）',
    PRIMARY KEY (id),
    KEY idx_detail_log (log_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='错误码修改记录字段明细表';
