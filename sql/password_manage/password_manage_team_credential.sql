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

 Date: 05/09/2026 14:12:33
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
  `platform_name` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '平台名称',
  `account_iv` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '用户名iv',
  `account_cipher` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '用户名加密密文',
  `password_iv` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '密码iv',
  `password_cipher` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '密码加密密文',
  `create_time` datetime NULL DEFAULT NULL,
  `is_deleted` tinyint(1) NULL DEFAULT 0 COMMENT '逻辑删除，0表示未删除，1表示删除',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `fk`(`team_id`) USING BTREE,
  CONSTRAINT `fk` FOREIGN KEY (`team_id`) REFERENCES `password_manage_team` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of password_manage_team_credential
-- ----------------------------
INSERT INTO `password_manage_team_credential` VALUES (3, 10, '蝴蝶云服务器', 'iv‑baidu', 'refreshaccount', 'iv‑pwd‑098765fedcba1234', 'refreshpassword', '2026-09-04 15:50:51', 0);
INSERT INTO `password_manage_team_credential` VALUES (4, 10, '百度云服务器', 'iv_0000000000000001', 'cipher_account_sample', 'iv_1111111111111112', 'cipher_password_sample', '2026-09-04 15:52:06', 0);
INSERT INTO `password_manage_team_credential` VALUES (5, 10, '蝴蝶刀服务器', 'iv‑baidu', 'refreshaccount', 'iv‑pwd‑098765fedcba1234', 'refreshpassword', '2026-09-05 13:27:18', 0);

SET FOREIGN_KEY_CHECKS = 1;
