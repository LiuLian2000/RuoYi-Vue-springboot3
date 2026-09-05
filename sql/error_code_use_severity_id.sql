-- 已存在的错误码管理库执行一次；新建库直接使用 error_code_mgmt_mall.sql。
-- 将错误码与严重程度的关联由可修改的业务代码改为稳定的主键 ID。
-- 本脚本可从上次执行中断后继续执行。

DROP PROCEDURE IF EXISTS migrate_error_code_severity_id;

DELIMITER //

CREATE PROCEDURE migrate_error_code_severity_id()
BEGIN
    DECLARE unmapped_count BIGINT DEFAULT 0;
    DECLARE severity_id_exists INT DEFAULT 0;
    DECLARE old_severity_exists INT DEFAULT 0;
    DECLARE old_index_exists INT DEFAULT 0;
    DECLARE new_index_exists INT DEFAULT 0;

    SELECT COUNT(*) INTO severity_id_exists
    FROM information_schema.columns
    WHERE table_schema = DATABASE()
      AND table_name = 'error_code'
      AND column_name = 'severity_id';

    IF severity_id_exists = 0 THEN
        ALTER TABLE error_code
            ADD COLUMN severity_id BIGINT UNSIGNED NULL COMMENT '严重程度ID' AFTER description;
    END IF;

    SELECT COUNT(*) INTO old_severity_exists
    FROM information_schema.columns
    WHERE table_schema = DATABASE()
      AND table_name = 'error_code'
      AND column_name = 'severity';

    IF old_severity_exists > 0 THEN
        -- 有效错误码必须关联未删除的严重程度。
        UPDATE error_code ec
        JOIN error_severity es
          ON es.severity_code = ec.severity
         AND es.del_flag = 0
        SET ec.severity_id = es.id
        WHERE ec.severity_id IS NULL;

        -- 已删除错误码只用于保留历史数据；找不到有效严重程度时允许关联历史严重程度。
        UPDATE error_code ec
        JOIN (
            SELECT severity_code, MAX(id) AS severity_id
            FROM error_severity
            GROUP BY severity_code
        ) history_severity
          ON history_severity.severity_code = ec.severity
        SET ec.severity_id = history_severity.severity_id
        WHERE ec.severity_id IS NULL
          AND ec.del_flag <> 0;

        SELECT COUNT(*) INTO unmapped_count
        FROM error_code
        WHERE severity_id IS NULL;

        IF unmapped_count > 0 THEN
            SELECT ec.id, ec.code, ec.severity, ec.del_flag
            FROM error_code ec
            WHERE ec.severity_id IS NULL
            ORDER BY ec.id;

            SIGNAL SQLSTATE '45000'
                SET MESSAGE_TEXT = '存在无法匹配严重程度的错误码，请先补齐 error_severity 数据后重试';
        END IF;

        ALTER TABLE error_code
            MODIFY COLUMN severity_id BIGINT UNSIGNED NOT NULL COMMENT '严重程度ID';

        SELECT COUNT(*) INTO old_index_exists
        FROM information_schema.statistics
        WHERE table_schema = DATABASE()
          AND table_name = 'error_code'
          AND index_name = 'idx_severity';

        IF old_index_exists > 0 THEN
            ALTER TABLE error_code DROP INDEX idx_severity;
        END IF;

        SELECT COUNT(*) INTO new_index_exists
        FROM information_schema.statistics
        WHERE table_schema = DATABASE()
          AND table_name = 'error_code'
          AND index_name = 'idx_severity_id';

        IF new_index_exists = 0 THEN
            ALTER TABLE error_code ADD KEY idx_severity_id (severity_id);
        END IF;

        ALTER TABLE error_code DROP COLUMN severity;
    END IF;
END//

DELIMITER ;

CALL migrate_error_code_severity_id();
DROP PROCEDURE migrate_error_code_severity_id;
