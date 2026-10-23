/*
 Navicat Premium Dump SQL

 Source Server         : docker_ruoyi
 Source Server Type    : MySQL
 Source Server Version : 50744 (5.7.44)
 Source Host           : localhost:3306
 Source Schema         : ry-vue

 Target Server Type    : MySQL
 Target Server Version : 50744 (5.7.44)
 File Encoding         : 65001

 Date: 12/10/2026 11:13:27
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for password_manage_team_credential
-- ----------------------------
DROP TABLE IF EXISTS `password_manage_team_credential`;
CREATE TABLE `password_manage_team_credential`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id主键',
  `team_id` bigint(20) NOT NULL COMMENT '密码所属团队id',
  `platform_name` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '平台名称',
  `platform_address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '平台网址',
  `account_salt` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '用户名iv',
  `account_cipher` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '用户名加密密文',
  `password_salt` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '密码iv',
  `password_cipher` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '密码加密密文',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP,
  `update_sys_user_id` bigint(20) NOT NULL COMMENT '最后一次更新该密码的用户的sys id',
  `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `remark` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL COMMENT '备注信息',
  `sharer_id` bigint(20) NULL DEFAULT NULL COMMENT '分享人sys用户id：个人密码分享入团队时记录分享人；原生团队凭据记录创建人；历史数据可为null',
  `is_deleted` tinyint(1) NULL DEFAULT 0 COMMENT '逻辑删除，0表示未删除，1表示删除',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `fk`(`team_id`) USING BTREE,
  CONSTRAINT `fk` FOREIGN KEY (`team_id`) REFERENCES `password_manage_team` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 28 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- 已存在的库执行以下语句补充新字段：
-- ALTER TABLE `password_manage_team_credential` ADD COLUMN `sharer_id` bigint(20) NULL DEFAULT NULL COMMENT '分享人sys用户id：个人密码分享入团队时记录分享人；原生团队凭据记录创建人' AFTER `remark`;

SET FOREIGN_KEY_CHECKS = 1;
