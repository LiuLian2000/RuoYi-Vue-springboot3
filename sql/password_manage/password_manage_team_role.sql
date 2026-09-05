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

 Date: 05/09/2026 14:12:40
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for password_manage_team_role
-- ----------------------------
DROP TABLE IF EXISTS `password_manage_team_role`;
CREATE TABLE `password_manage_team_role`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `team_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '团队名称',
  `team_id` bigint(20) NOT NULL COMMENT '团队id',
  `user_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '成员用户名',
  `user_id` bigint(20) NOT NULL COMMENT '成员用户id',
  `team_role` tinyint(1) NOT NULL COMMENT '该成员在团队中的角色，0-admin，1-mebmer',
  `is_deleted` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否删除',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '记录创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '记录更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `fk_team`(`team_id`) USING BTREE,
  INDEX `fk_user`(`user_id`) USING BTREE,
  CONSTRAINT `fk_team` FOREIGN KEY (`team_id`) REFERENCES `password_manage_team` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION,
  CONSTRAINT `fk_user` FOREIGN KEY (`user_id`) REFERENCES `password_manage_user` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE = InnoDB AUTO_INCREMENT = 10 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of password_manage_team_role
-- ----------------------------
INSERT INTO `password_manage_team_role` VALUES (6, '测试内部密码组', 10, 'test', 2, 0, 0, '2026-09-04 11:32:59', '2026-09-04 11:32:59');
INSERT INTO `password_manage_team_role` VALUES (7, '测试内部密码组', 10, 'test2', 3, 1, 1, '2026-09-05 13:54:12', '2026-09-05 13:54:12');
INSERT INTO `password_manage_team_role` VALUES (8, '测试内部密码组', 10, 'admin', 1, 1, 0, '2026-09-04 15:39:30', '2026-09-04 15:39:29');
INSERT INTO `password_manage_team_role` VALUES (9, '开发内部密码组', 11, 'test2', 3, 1, 0, '2026-09-05 12:49:36', '2026-09-05 12:49:36');

SET FOREIGN_KEY_CHECKS = 1;
