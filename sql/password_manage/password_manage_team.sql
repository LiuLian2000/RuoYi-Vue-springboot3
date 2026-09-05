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

 Date: 05/09/2026 14:12:26
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for password_manage_team
-- ----------------------------
DROP TABLE IF EXISTS `password_manage_team`;
CREATE TABLE `password_manage_team`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `team_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '团队名称',
  `create_user_id` bigint(20) NOT NULL COMMENT '创建人ID,绑定password_manage_user的id',
  `user_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '创建人账号',
  `nick_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '创建人昵称',
  `random_salt` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '团队密钥加密salt',
  `team_password` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '团队密钥加密随机iv',
  `encrypted_team_key_cipher` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '团队密钥加密后密文',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '备注',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `is_deleted` tinyint(4) NOT NULL DEFAULT 0 COMMENT '逻辑删除 0-未删除 1-已删除',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `for_key1`(`create_user_id`) USING BTREE,
  CONSTRAINT `for_key1` FOREIGN KEY (`create_user_id`) REFERENCES `password_manage_user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 12 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of password_manage_team
-- ----------------------------
INSERT INTO `password_manage_team` VALUES (10, '测试内部密码组', 2, 'test', 'test', 'c29tZS1yYW5kb20tc2FsdC1kYXRh', 'password', 'ZW5jcnlwdGVkLXRlYW0ta2V5LWNpcGhlci1kYXRhLWJhc2U2NA==', '存放测试第三方账号密码', '2026-09-04 11:32:59', '2026-09-04 11:32:59', 0);
INSERT INTO `password_manage_team` VALUES (11, '开发内部密码组', 2, 'test', 'test', 'randomSalt', 'password2', 'teamKey', '存放开发第三方账号', '2026-09-04 16:07:25', '2026-09-04 16:07:25', 0);

SET FOREIGN_KEY_CHECKS = 1;
