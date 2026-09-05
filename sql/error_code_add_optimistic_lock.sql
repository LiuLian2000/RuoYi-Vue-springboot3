-- 已存在的错误码管理库执行；新建库直接使用 error_code_mgmt_mall.sql。
-- 可重复执行：只给尚未包含 version 字段的表添加字段。

DROP PROCEDURE IF EXISTS add_error_code_optimistic_lock;

DELIMITER //

CREATE PROCEDURE add_error_code_optimistic_lock()
BEGIN
    IF NOT EXISTS (
        SELECT 1 FROM information_schema.columns
        WHERE table_schema = DATABASE()
          AND table_name = 'error_system'
          AND column_name = 'version'
    ) THEN
        ALTER TABLE error_system
            ADD COLUMN version INT UNSIGNED NOT NULL DEFAULT 0
            COMMENT '乐观锁版本号' AFTER status;
    END IF;

    IF NOT EXISTS (
        SELECT 1 FROM information_schema.columns
        WHERE table_schema = DATABASE()
          AND table_name = 'error_category'
          AND column_name = 'version'
    ) THEN
        ALTER TABLE error_category
            ADD COLUMN version INT UNSIGNED NOT NULL DEFAULT 0
            COMMENT '乐观锁版本号' AFTER status;
    END IF;

    IF NOT EXISTS (
        SELECT 1 FROM information_schema.columns
        WHERE table_schema = DATABASE()
          AND table_name = 'error_severity'
          AND column_name = 'version'
    ) THEN
        ALTER TABLE error_severity
            ADD COLUMN version INT UNSIGNED NOT NULL DEFAULT 0
            COMMENT '乐观锁版本号' AFTER status;
    END IF;

    IF NOT EXISTS (
        SELECT 1 FROM information_schema.columns
        WHERE table_schema = DATABASE()
          AND table_name = 'error_code'
          AND column_name = 'version'
    ) THEN
        ALTER TABLE error_code
            ADD COLUMN version INT UNSIGNED NOT NULL DEFAULT 0
            COMMENT '乐观锁版本号' AFTER status;
    END IF;
END//

DELIMITER ;

CALL add_error_code_optimistic_lock();
DROP PROCEDURE add_error_code_optimistic_lock;
