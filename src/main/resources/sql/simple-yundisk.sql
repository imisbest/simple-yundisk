/*
 Navicat Premium Data Transfer

 Source Server         : 192.168.147.131_3306
 Source Server Type    : MySQL
 Source Server Version : 50642
 Source Host           : 192.168.147.131:3306
 Source Schema         : simple-yundisk

 Target Server Type    : MySQL
 Target Server Version : 50642
 File Encoding         : 65001

 Date: 08/04/2020 20:26:22
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for black_p
-- ----------------------------
DROP TABLE IF EXISTS `black_p`;
CREATE TABLE `black_p`  (
  `uid` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `count` int(9) NULL DEFAULT NULL,
  PRIMARY KEY (`uid`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of black_p
-- ----------------------------
INSERT INTO `black_p` VALUES ('09e28c93-7752-4ff3-bd5d-fd4e95539514', 3);
INSERT INTO `black_p` VALUES ('4b03c7c0-a6ca-446b-b4a4-7f8ad5f044fa', 3);
INSERT INTO `black_p` VALUES ('8a0c239d-8463-43ec-ac48-39fbba7964d4', 3);

-- ----------------------------
-- Table structure for dir_p
-- ----------------------------
DROP TABLE IF EXISTS `dir_p`;
CREATE TABLE `dir_p`  (
  `id` varchar(222) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `path` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `count` int(99) NULL DEFAULT NULL,
  `create_time` datetime(0) NULL DEFAULT NULL,
  `status` int(1) NULL DEFAULT NULL,
  `is_share` int(1) NULL DEFAULT NULL,
  `icon` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `pid` varchar(222) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `uid` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of dir_p
-- ----------------------------
INSERT INTO `dir_p` VALUES ('8a40ec1e-607e-40c4-99f6-2bcad4b791c8', '4b03c7c0-a6ca-446b-b4a4-7f8ad5f044fa', 'D:\\IdeaProjects\\IdeaProjects\\simple-yundisk\\src\\main\\webapp\\userSave', 0, '2020-04-06 13:56:36', 1, 0, '文件夹', NULL, '4b03c7c0-a6ca-446b-b4a4-7f8ad5f044fa');
INSERT INTO `dir_p` VALUES ('c36513ea-5b3b-478d-be9c-e42ca5a2407d', '09e28c93-7752-4ff3-bd5d-fd4e95539514', 'D:\\IdeaProjects\\IdeaProjects\\simple-yundisk\\src\\main\\webapp\\userSave', 0, '2020-04-06 12:38:39', 1, 0, '文件夹', NULL, '09e28c93-7752-4ff3-bd5d-fd4e95539514');
INSERT INTO `dir_p` VALUES ('d1c64ebd-6d4b-405f-bcd5-09fe4b1dd83d', '8a0c239d-8463-43ec-ac48-39fbba7964d4', 'D:\\IdeaProjects\\IdeaProjects\\simple-yundisk\\src\\main\\webapp\\userSave', 0, '2020-04-06 12:37:23', 1, 0, '文件夹', NULL, '8a0c239d-8463-43ec-ac48-39fbba7964d4');

-- ----------------------------
-- Table structure for file_p
-- ----------------------------
DROP TABLE IF EXISTS `file_p`;
CREATE TABLE `file_p`  (
  `id` varchar(222) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `new_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `z_file` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `path` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `sizes` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `type` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `create_time` datetime(0) NULL DEFAULT NULL,
  `option_time` datetime(0) NULL DEFAULT NULL,
  `count` int(99) NULL DEFAULT NULL,
  `status` int(1) NULL DEFAULT NULL,
  `check_md5` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `is_share` int(1) NULL DEFAULT NULL,
  `icon` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `pid` varchar(222) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `uid` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for friend_p
-- ----------------------------
DROP TABLE IF EXISTS `friend_p`;
CREATE TABLE `friend_p`  (
  `id` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `uid` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `fid` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of friend_p
-- ----------------------------
INSERT INTO `friend_p` VALUES ('5cf5e478-416b-44d4-a761-db133bb2f9ff', '8a0c239d-8463-43ec-ac48-39fbba7964d4', '09e28c93-7752-4ff3-bd5d-fd4e95539514');
INSERT INTO `friend_p` VALUES ('60090610-1be8-4a22-bac3-0c8ddcdb371d', '09e28c93-7752-4ff3-bd5d-fd4e95539514', '8a0c239d-8463-43ec-ac48-39fbba7964d4');
INSERT INTO `friend_p` VALUES ('66bc5224-4b30-4a8d-9271-1059de74dd66', '8a0c239d-8463-43ec-ac48-39fbba7964d4', '4b03c7c0-a6ca-446b-b4a4-7f8ad5f044fa');
INSERT INTO `friend_p` VALUES ('faab568d-e94f-486e-8ee2-4026907926ed', '4b03c7c0-a6ca-446b-b4a4-7f8ad5f044fa', '8a0c239d-8463-43ec-ac48-39fbba7964d4');

-- ----------------------------
-- Table structure for share_f
-- ----------------------------
DROP TABLE IF EXISTS `share_f`;
CREATE TABLE `share_f`  (
  `id` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `from_id` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `to_id` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `file_id` varchar(222) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `dir_id` varchar(222) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for user_p
-- ----------------------------
DROP TABLE IF EXISTS `user_p`;
CREATE TABLE `user_p`  (
  `id` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `email` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `pwd` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `phone` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `code` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `salt` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `status` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `create_date` datetime(0) NULL DEFAULT NULL,
  `login_time` datetime(0) NULL DEFAULT NULL,
  `hide_pwd` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `hide_salt` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of user_p
-- ----------------------------
INSERT INTO `user_p` VALUES ('09e28c93-7752-4ff3-bd5d-fd4e95539514', '17269671598', '17269671598', 'c4ca4238a0b923820dcc509a6f75849bYaKMSnRh', '17269671598', '961819', 'YaKMSnRh', '正常', '2020-04-06 12:38:39', NULL, NULL, NULL);
INSERT INTO `user_p` VALUES ('4b03c7c0-a6ca-446b-b4a4-7f8ad5f044fa', '3', '3', 'eccbc87e4b5ce2fe28308fd9f2a7baf3usuTv1HY', '3', '070154', 'usuTv1HY', '正常', '2020-04-06 13:56:36', NULL, NULL, NULL);
INSERT INTO `user_p` VALUES ('8a0c239d-8463-43ec-ac48-39fbba7964d4', '17337815026', '17337815026@qq.com', 'c4ca4238a0b923820dcc509a6f75849baQXjLtyZ', '17337815026', '561116', 'aQXjLtyZ', '正常', '2020-04-06 12:37:23', NULL, 'c4ca4238a0b923820dcc509a6f75849bxK3SKbUw', 'xK3SKbUw');

SET FOREIGN_KEY_CHECKS = 1;
