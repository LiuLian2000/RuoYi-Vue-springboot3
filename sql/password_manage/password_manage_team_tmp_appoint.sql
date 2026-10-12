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

 Date: 12/10/2026 11:13:55
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for password_manage_team_tmp_appoint
-- ----------------------------
DROP TABLE IF EXISTS `password_manage_team_tmp_appoint`;
CREATE TABLE `password_manage_team_tmp_appoint`  (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `team_id` bigint(20) NULL DEFAULT NULL COMMENT '团队id',
  `sysusr_id` bigint(20) NULL DEFAULT NULL COMMENT '用户的系统用户id',
  `encrypted_cipher` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL COMMENT '金库密钥使用团队成员公钥加密后的密文',
  `is_deleted` tinyint(1) NULL DEFAULT 0 COMMENT '删除标志',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '临时约定凭证创建时间',
  `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '临时约定凭证修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '该表用于存储团队新增成员时所需使用的临时约定凭证' ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
