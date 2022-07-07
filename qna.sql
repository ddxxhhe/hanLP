/*
Navicat MySQL Data Transfer

Source Server         : hanlp
Source Server Version : 80021
Source Host           : localhost:3306
Source Database       : qa

Target Server Type    : MYSQL
Target Server Version : 80021
File Encoding         : 65001

Date: 2022-07-07 23:06:47
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for qna
-- ----------------------------
DROP TABLE IF EXISTS `qna`;
CREATE TABLE `qna` (
  `id` int NOT NULL,
  `ques` varchar(255) DEFAULT NULL,
  `ans` varchar(255) DEFAULT NULL,
  `score` double DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of qna
-- ----------------------------
INSERT INTO `qna` VALUES ('1', '上海市长是谁？', '龚正', null);
INSERT INTO `qna` VALUES ('2', '上海有多少市辖区？', '16', null);
INSERT INTO `qna` VALUES ('3', '上海属于哪个地区？', '华东地区', null);
INSERT INTO `qna` VALUES ('4', '上海有哪些高校？', '复旦大学、上海交通大学、同济大学、华东师范大学等', null);
INSERT INTO `qna` VALUES ('5', '中国的国花是什么？', '牡丹', null);
INSERT INTO `qna` VALUES ('6', '世界第一高峰是什么？', '珠穆朗玛峰', null);
