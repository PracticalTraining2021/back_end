/*
 Navicat MySQL Data Transfer

 Source Server         : conn0
 Source Server Type    : MySQL
 Source Server Version : 80021
 Source Host           : localhost:3306
 Source Schema         : taptap

 Target Server Type    : MySQL
 Target Server Version : 80021
 File Encoding         : 65001

 Date: 18/06/2021 19:53:04
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for comment
-- ----------------------------
DROP TABLE IF EXISTS `comment`;
CREATE TABLE `comment`  (
  `comment_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `game_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `user_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `content` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `score` int NULL DEFAULT NULL,
  `likes_count` int NULL DEFAULT NULL,
  `comment_at` date NULL DEFAULT NULL,
  PRIMARY KEY (`comment_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for dynamic
-- ----------------------------
DROP TABLE IF EXISTS `dynamic`;
CREATE TABLE `dynamic`  (
  `dynamic_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `game_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `user_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `content` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `img_urls` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `likes_count` int NULL DEFAULT NULL,
  `publish_at` date NULL DEFAULT NULL,
  PRIMARY KEY (`dynamic_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for game
-- ----------------------------
DROP TABLE IF EXISTS `game`;
CREATE TABLE `game`  (
  `game_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `size` int NULL DEFAULT NULL,
  `issuer` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `downloads` int NULL DEFAULT NULL,
  `avg_score` double NULL DEFAULT NULL,
  `comment_count` int NULL DEFAULT NULL,
  `interest_count` int NULL DEFAULT NULL,
  `icon` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `display_drawings` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `brief_intro` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`game_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `user_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `nickname` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `username` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `password` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `avatar` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `gender` int NULL DEFAULT NULL,
  `create_at` date NULL DEFAULT NULL,
  `last_login_at` date NULL DEFAULT NULL,
  PRIMARY KEY (`user_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for user_likes_comment
-- ----------------------------
DROP TABLE IF EXISTS `user_likes_comment`;
CREATE TABLE `user_likes_comment`  (
  `comment_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `user_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`comment_id`, `user_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for user_likes_dynamic
-- ----------------------------
DROP TABLE IF EXISTS `user_likes_dynamic`;
CREATE TABLE `user_likes_dynamic`  (
  `user_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `dynamic_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`user_id`, `dynamic_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for user_likes_game
-- ----------------------------
DROP TABLE IF EXISTS `user_likes_game`;
CREATE TABLE `user_likes_game`  (
  `user_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `game_Id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`user_id`, `game_Id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
