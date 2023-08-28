/*
Navicat MySQL Data Transfer

Source Server         : 本地
Source Server Version : 50722
Source Host           : localhost:3306
Source Database       : ktf

Target Server Type    : MYSQL
Target Server Version : 50722
File Encoding         : 65001

Date: 2019-09-27 23:33:37
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for ktf_sys_dic
-- ----------------------------
DROP TABLE IF EXISTS `ktf_sys_dic`;
CREATE TABLE `ktf_sys_dic` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `parent_id` bigint(20) NOT NULL COMMENT '父变量ID',
  `var_code` varchar(255) NOT NULL COMMENT '变量代码',
  `var_name` varchar(255) NOT NULL COMMENT '变量名称',
  `is_sync` tinyint(4) DEFAULT NULL COMMENT '数据是否同步(0:是,1:否)',
  `gmt_create` datetime DEFAULT NULL COMMENT '记录创建时间',
  `gmt_update` datetime DEFAULT NULL COMMENT '记录修改时间',
  `create_user` varchar(255) DEFAULT NULL COMMENT '记录创建者（用户）',
  `update_user` varchar(255) DEFAULT NULL COMMENT '记录最后修改者（用户）',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `index_dic_code` (`var_code`) USING BTREE,
  KEY `index_dic_name` (`var_name`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=266 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='数据字典';

-- ----------------------------
-- Records of ktf_sys_dic
-- ----------------------------
INSERT INTO `ktf_sys_dic` VALUES ('1', '0', '20190901', '设备类型', null, '2019-09-16 23:47:11', '2019-09-16 23:47:27', null, null);
INSERT INTO `ktf_sys_dic` VALUES ('2', '0', '20190902', '通讯类型', null, '2019-09-16 23:47:15', '2019-09-16 23:47:31', null, null);
INSERT INTO `ktf_sys_dic` VALUES ('3', '0', '20190903', 'NB运营商', null, '2019-09-16 23:47:18', '2019-09-16 23:47:34', null, null);
INSERT INTO `ktf_sys_dic` VALUES ('255', '1', 'DoorLock', '智能家居', null, '2019-09-06 22:39:58', '2019-09-16 23:47:36', 'admin', null);
INSERT INTO `ktf_sys_dic` VALUES ('256', '1', 'Robot', '机器人', null, '2019-09-06 22:40:31', '2019-09-16 23:47:39', 'admin', null);
INSERT INTO `ktf_sys_dic` VALUES ('257', '1', 'UAV', '无人机', null, '2019-09-06 22:40:57', '2019-09-16 23:47:42', 'admin', null);
INSERT INTO `ktf_sys_dic` VALUES ('258', '1', 'DTU', '工控产品', null, '2019-09-06 22:41:13', '2019-09-16 23:47:45', 'admin', null);
INSERT INTO `ktf_sys_dic` VALUES ('259', '2', 'NB', 'NB', null, '2019-09-06 22:42:07', '2019-09-16 23:47:48', 'admin', null);
INSERT INTO `ktf_sys_dic` VALUES ('260', '2', 'WIFI', 'WIFI', null, '2019-09-06 22:42:28', '2019-09-16 23:47:51', 'admin', null);
INSERT INTO `ktf_sys_dic` VALUES ('261', '2', 'BT', '蓝牙', null, '2019-09-06 22:42:52', '2019-09-16 23:47:54', 'admin', null);
INSERT INTO `ktf_sys_dic` VALUES ('262', '3', 'CTCC', '电信', null, '2019-09-06 22:43:26', '2019-09-06 22:43:36', 'admin', 'admin');
INSERT INTO `ktf_sys_dic` VALUES ('263', '3', 'CUCC', '联通', null, '2019-09-06 22:44:00', '2019-09-16 23:47:58', 'admin', null);
INSERT INTO `ktf_sys_dic` VALUES ('264', '3', 'CUCC', '联通', null, '2019-09-06 22:44:02', '2019-09-16 23:48:01', 'admin', null);
INSERT INTO `ktf_sys_dic` VALUES ('265', '3', 'CMCC', '移动', null, '2019-09-06 22:44:16', '2019-09-16 23:48:04', 'admin', null);
