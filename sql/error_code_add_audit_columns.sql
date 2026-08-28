-- 已有数据库的增量升级脚本
ALTER TABLE error_system
    ADD COLUMN create_by VARCHAR(64) DEFAULT NULL COMMENT '创建者' AFTER del_flag,
    ADD COLUMN update_by VARCHAR(64) DEFAULT NULL COMMENT '更新者' AFTER create_time;

ALTER TABLE error_category
    ADD COLUMN create_by VARCHAR(64) DEFAULT NULL COMMENT '创建者' AFTER del_flag,
    ADD COLUMN update_by VARCHAR(64) DEFAULT NULL COMMENT '更新者' AFTER create_time;

ALTER TABLE error_severity
    ADD COLUMN create_by VARCHAR(64) DEFAULT NULL COMMENT '创建者' AFTER del_flag,
    ADD COLUMN update_by VARCHAR(64) DEFAULT NULL COMMENT '更新者' AFTER create_time;

ALTER TABLE error_code
    ADD COLUMN create_by VARCHAR(64) DEFAULT NULL COMMENT '创建者' AFTER del_flag,
    ADD COLUMN update_by VARCHAR(64) DEFAULT NULL COMMENT '更新者' AFTER create_time;
