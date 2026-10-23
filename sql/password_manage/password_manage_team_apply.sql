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

 Date: 23/10/2026 12:00:00
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for password_manage_team_apply
-- ----------------------------
DROP TABLE IF EXISTS `password_manage_team_apply`;
CREATE TABLE `password_manage_team_apply`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `team_id` bigint(20) NOT NULL COMMENT '团队id',
  `team_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '团队名称(冗余,防团队改名后失联)',
  `applicant_pm_user_id` bigint(20) NOT NULL COMMENT '申请人 password_manage_user.id',
  `applicant_user_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '申请人用户名(冗余)',
  `apply_reason` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '申请理由',
  `status` tinyint(4) NOT NULL DEFAULT 0 COMMENT '0待审 1通过 2拒绝',
  `reject_reason` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '拒绝理由',
  `reviewer_pm_user_id` bigint(20) NULL DEFAULT NULL COMMENT '审批人 password_manage_user.id',
  `reviewer_user_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '审批人用户名',
  `is_deleted` tinyint(1) NOT NULL DEFAULT 0 COMMENT '逻辑删除',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE,
  KEY `idx_team_status` (`team_id`, `status`) USING BTREE,
  KEY `idx_applicant` (`applicant_pm_user_id`, `status`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic COMMENT = '团队入队申请';

SET FOREIGN_KEY_CHECKS = 1;
