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

 Date: 05/09/2026 14:13:30
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for password_manage_user
-- ----------------------------
DROP TABLE IF EXISTS `password_manage_user`;
CREATE TABLE `password_manage_user`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `sys_user_id` bigint(20) NOT NULL,
  `random_salt` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `encrypted_vault_iv` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `encrypted_vault_cipher` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `fk_user_id`(`sys_user_id`) USING BTREE,
  CONSTRAINT `fk_user_id` FOREIGN KEY (`sys_user_id`) REFERENCES `sys_user` (`user_id`) ON DELETE CASCADE ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '用户密码管理主表，绑定若依系统的sys_user表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of password_manage_user
-- ----------------------------
INSERT INTO `password_manage_user` VALUES (1, 1, 'salt', 'iv', 'cipher');
INSERT INTO `password_manage_user` VALUES (2, 100, 'userSalt', 'userIv', 'userCipher');
INSERT INTO `password_manage_user` VALUES (3, 101, 'userSalt2', 'userIv2', 'userCipher2');

SET FOREIGN_KEY_CHECKS = 1;
