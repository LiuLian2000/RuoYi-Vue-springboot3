-- ----------------------------
-- 修复：新用户未初始化个人金库时，直接进入团队密码管理「解密金库」报
-- “当前数据操作异常，请联系管理员。”
--
-- 原因：password_manage_user.encrypted_vault_iv / encrypted_vault_cipher 为 NOT NULL，
--       而记录可能先由团队模块创建（个人金库信封字段此前未生成）。
-- 处理：前端已改为创建记录时一并生成个人金库信封；此处再将两列放宽为可空，
--       兼容历史/异常数据，防御同类插入失败。
-- 执行方式：在 ry-vue 库执行一次即可。
-- ----------------------------

ALTER TABLE `password_manage_user`
    MODIFY COLUMN `encrypted_vault_iv` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL COMMENT '用户个人密码管理的金库密钥加密所使用的随机向量';

ALTER TABLE `password_manage_user`
    MODIFY COLUMN `encrypted_vault_cipher` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL COMMENT '用户个人密码管理的金库密钥加密密文';
