/*
SQLyog 企业版 - MySQL GUI v8.14 
MySQL - 5.5.35 : Database - yaw
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
/*Table structure for table `yaw_apply_authentication` */

DROP TABLE IF EXISTS `yaw_apply_authentication`;

CREATE TABLE `yaw_apply_authentication` (
  `AUTH_ID` int(11) NOT NULL AUTO_INCREMENT,
  `AUTH_MNG_ID` varchar(32) DEFAULT NULL,
  `AUTH_MID` varchar(32) DEFAULT NULL,
  `AUTH_DATE` datetime DEFAULT NULL,
  `AUTH_TYPE` tinyint(4) DEFAULT NULL COMMENT '1：视频认证\n            2：身份认证\n            3：导游认证\n            4：健康认证\n            5：加入伴游俱乐部申请',
  `AUTH_RESULT` tinyint(4) DEFAULT NULL,
  `AUTH_RESON` varchar(200) DEFAULT NULL,
  `AUTH_AUDIT_TIME` datetime DEFAULT NULL,
  PRIMARY KEY (`AUTH_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8 COMMENT='会员申请视频,身份,健康等认证申请记录表';

/*Data for the table `yaw_apply_authentication` */

insert  into `yaw_apply_authentication`(`AUTH_ID`,`AUTH_MNG_ID`,`AUTH_MID`,`AUTH_DATE`,`AUTH_TYPE`,`AUTH_RESULT`,`AUTH_RESON`,`AUTH_AUDIT_TIME`) values (1,'admin','admin',NULL,2,1,'可以','2015-07-25 00:08:46'),(2,'admin','admin',NULL,5,0,'可以','2015-07-25 14:06:10'),(4,'admin','admin','2015-07-25 00:29:37',6,1,'可以','2015-07-25 14:02:17'),(5,'admin','mng','2015-07-25 14:13:32',9,1,'ok','2015-07-25 15:17:04'),(6,'admin','mng','2015-07-25 14:15:22',9,1,'感觉还行','2015-08-29 16:24:48'),(7,NULL,'mng','2015-08-13 15:37:13',3,0,NULL,NULL),(8,NULL,'admin','2015-08-23 17:55:30',3,0,NULL,NULL);

/*Table structure for table `yaw_change_record` */

DROP TABLE IF EXISTS `yaw_change_record`;

CREATE TABLE `yaw_change_record` (
  `变更ID` int(11) NOT NULL AUTO_INCREMENT,
  `MA_LOGIN_NAME` varchar(32) DEFAULT NULL,
  `变更类型` char(10) DEFAULT NULL,
  `变更时间` char(10) DEFAULT NULL,
  `变更内容` char(10) DEFAULT NULL,
  PRIMARY KEY (`变更ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='存储会员资料、相册，形象，联系方式等方面的变更';

/*Data for the table `yaw_change_record` */

/*Table structure for table `yaw_escort_info` */

DROP TABLE IF EXISTS `yaw_escort_info`;

CREATE TABLE `yaw_escort_info` (
  `ESCORT_MID` varchar(32) NOT NULL,
  `ESCORT_NAME` varchar(30) DEFAULT NULL,
  `ESCORT_NICK_NAME` varchar(30) DEFAULT NULL,
  `ESCORT_BIRTHDAY` date DEFAULT NULL,
  `ESCORT_SEX` varchar(1) DEFAULT NULL,
  `ESCORT_LIVE_ADDR` varchar(20) DEFAULT NULL,
  `ESCORT_DRIVE_YEAR` int(11) DEFAULT '0',
  `ESCORT_BODY` tinyint(4) DEFAULT NULL COMMENT '0:  未填写\n            1：苗条\n            2：GU感\n            3：肉感\n            4：丰满\n            5：火辣\n            6：匀称',
  `ESCORT_WEIGHT` tinyint(4) DEFAULT NULL,
  `ESCORT_HEIGHT` int(11) DEFAULT NULL,
  `ESCORT_IMAGE` tinyint(4) DEFAULT NULL COMMENT '0:   未填\n            1：清秀\n            2：阳光\n            3：可爱\n            4：甜美\n            5:性感',
  `ESCORT_FEEL` tinyint(4) DEFAULT NULL COMMENT '0：知性\n            1：性感\n            2：妩媚\n            3：妖艳\n            4：中性\n            ',
  `ESCORT_FACE_PIC` varchar(255) DEFAULT NULL,
  `ESCORT_LOVE` varchar(100) DEFAULT NULL,
  `ESCORT_JOB` tinyint(4) DEFAULT NULL COMMENT '0:未填写\n            1:教育培训\n            2\n            3\n            4\n            ',
  `ESCORT_QQ` varchar(20) DEFAULT NULL,
  `ESCORT_WECHAT` varchar(20) DEFAULT NULL,
  `ESCORT_PHONE` varchar(20) DEFAULT NULL,
  `ESCORT_PRICE` int(11) DEFAULT '0' COMMENT 'n:元/天\n            -1：面议',
  `ESCORT_RECOMMEND` varchar(500) DEFAULT NULL,
  `ESCORT_TYPE` tinyint(4) DEFAULT NULL COMMENT '0：未填定\n            1：私人陪游\n            2：交友私会\n            3：向导导游\n            4：交友蹭游\n            5：商务秘书',
  `ESCORT_EXP` tinyint(4) DEFAULT NULL COMMENT '1：职业伴游\n            2：有过几次\n            3：没有经验\n            4:   执证导游\n            0:  未填写\n            ',
  `ESCORT_TRYTO` varchar(100) DEFAULT NULL,
  `ESCORT_LANGUAGE` varchar(10) DEFAULT NULL,
  `ESCORT_ATTRACTIVE` varchar(5) DEFAULT NULL,
  `ESCORT_TRIP_ADDR` varchar(20) DEFAULT NULL COMMENT '没填，就是哪都可以。',
  `ESCORT_CLUB_MEMBER` tinyint(4) DEFAULT NULL COMMENT '0:不是\n            1:是',
  `ESCORT_ORDER_WEIGHT` int(11) DEFAULT NULL COMMENT '值越大，权重越高，排名越靠前\n            根据订购\n             增值服务，会员级别，诚意度、积分数，资料完整度 共五项，依序算法来综合设定该值',
  PRIMARY KEY (`ESCORT_MID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `yaw_escort_info` */

insert  into `yaw_escort_info`(`ESCORT_MID`,`ESCORT_NAME`,`ESCORT_NICK_NAME`,`ESCORT_BIRTHDAY`,`ESCORT_SEX`,`ESCORT_LIVE_ADDR`,`ESCORT_DRIVE_YEAR`,`ESCORT_BODY`,`ESCORT_WEIGHT`,`ESCORT_HEIGHT`,`ESCORT_IMAGE`,`ESCORT_FEEL`,`ESCORT_FACE_PIC`,`ESCORT_LOVE`,`ESCORT_JOB`,`ESCORT_QQ`,`ESCORT_WECHAT`,`ESCORT_PHONE`,`ESCORT_PRICE`,`ESCORT_RECOMMEND`,`ESCORT_TYPE`,`ESCORT_EXP`,`ESCORT_TRYTO`,`ESCORT_LANGUAGE`,`ESCORT_ATTRACTIVE`,`ESCORT_TRIP_ADDR`,`ESCORT_CLUB_MEMBER`,`ESCORT_ORDER_WEIGHT`) values ('admin',NULL,NULL,NULL,'女',NULL,0,0,0,0,0,0,NULL,NULL,0,'21617198','12145878','13378017836',0,NULL,0,0,NULL,NULL,NULL,NULL,0,776),('mng','张小丰','测试帐号','1994-12-28','女','武汉',2,2,60,170,2,2,NULL,'足球,音乐,运动',2,'21617198','12145878','13378017836',3000,'自我推荐的赎回',1,1,'长沙,武汉','中文,英文','有点美','长沙,武汉',0,223),('小朋友',NULL,NULL,NULL,'女',NULL,0,0,0,0,0,0,NULL,NULL,0,NULL,NULL,NULL,0,NULL,0,0,NULL,NULL,NULL,NULL,0,0);

/*Table structure for table `yaw_increment_service` */

DROP TABLE IF EXISTS `yaw_increment_service`;

CREATE TABLE `yaw_increment_service` (
  `INCSERVICE_ID` int(11) NOT NULL AUTO_INCREMENT,
  `INCSERVICE_NAME` varchar(20) DEFAULT NULL,
  `INCSERVICE_SUIT_DAY` int(11) DEFAULT NULL,
  `INCSERVICE_PRICE` int(11) DEFAULT NULL,
  PRIMARY KEY (`INCSERVICE_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=53 DEFAULT CHARSET=utf8 COMMENT='存储平台的几种增值服务项目；该表暂时先预给定数据,后面再提供管理操作功能;\n约啊币本身是一种增值服务，单价一';

/*Data for the table `yaw_increment_service` */

insert  into `yaw_increment_service`(`INCSERVICE_ID`,`INCSERVICE_NAME`,`INCSERVICE_SUIT_DAY`,`INCSERVICE_PRICE`) values (1,'约啊币',1,1),(3,'伴游之首',30,500),(51,'蓝钻会员',30,300),(52,'黄钻会员',30,200);

/*Table structure for table `yaw_login_log` */

DROP TABLE IF EXISTS `yaw_login_log`;

CREATE TABLE `yaw_login_log` (
  `LOGIN_ID` int(11) NOT NULL,
  `LOGIN_MID` varchar(32) DEFAULT NULL,
  `LOGIN_DATE` datetime DEFAULT NULL,
  `LOGIN_IP` varchar(40) DEFAULT NULL,
  `LOGIN_ISPHONE` tinyint(4) DEFAULT NULL COMMENT '0:不是\n            1:是',
  `LOGIN_LENGTH` int(11) DEFAULT NULL,
  `LOGIN_IP_ADDR` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`LOGIN_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `yaw_login_log` */

/*Table structure for table `yaw_manager_account` */

DROP TABLE IF EXISTS `yaw_manager_account`;

CREATE TABLE `yaw_manager_account` (
  `MNG_LOGIN_NAME` varchar(32) NOT NULL,
  `MNG_PASSWORD` varchar(32) DEFAULT NULL,
  `MNG_NAME` varchar(16) DEFAULT NULL,
  `MNG_TYPE` tinyint(4) DEFAULT NULL COMMENT '0:客服\n            1:管理员\n            2:超级管理员',
  `MNG_LOGIN_TIME` datetime DEFAULT NULL,
  `MNG_LOGIN_IP` varchar(64) DEFAULT NULL,
  `MNG_LOGIN_LENGTH` int(11) DEFAULT '0',
  `MNG_STATUS` tinyint(4) DEFAULT '1' COMMENT '是否锁定；1：正常，2：锁定\n            ',
  `MNG_ONLINE` tinyint(4) DEFAULT '0' COMMENT '0：离线，1:在线',
  PRIMARY KEY (`MNG_LOGIN_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `yaw_manager_account` */

insert  into `yaw_manager_account`(`MNG_LOGIN_NAME`,`MNG_PASSWORD`,`MNG_NAME`,`MNG_TYPE`,`MNG_LOGIN_TIME`,`MNG_LOGIN_IP`,`MNG_LOGIN_LENGTH`,`MNG_STATUS`,`MNG_ONLINE`) values ('admin','091107cea97e86fe37af0687eb8aa2d1','超级管理员',2,'2015-08-29 15:52:14','127.0.0.1',90,1,1);

/*Table structure for table `yaw_member_account` */

DROP TABLE IF EXISTS `yaw_member_account`;

CREATE TABLE `yaw_member_account` (
  `MA_LOGIN_NAME` varchar(32) NOT NULL,
  `MA_SERVICE_ID` varchar(100) DEFAULT NULL COMMENT '一些外部服务或设备的唯标识号',
  `MA_PASSWORD` varchar(32) NOT NULL,
  `MA_TYPE` tinyint(4) DEFAULT NULL COMMENT '0:游客\n            1:伴游\n            ',
  `MA_REGIST_TIME` datetime DEFAULT NULL,
  `MA_LOGIN_TIME` datetime DEFAULT NULL,
  `MA_LOGIN_IP` varchar(40) DEFAULT NULL,
  `MA_ONLINE_LONG` int(11) DEFAULT NULL,
  `MA_YA_COIN` int(11) DEFAULT NULL COMMENT '可以当人民币用，是买来的或推广、分享赚来的',
  `MA_EMAIL` varchar(50) DEFAULT NULL,
  `MA_POINTS` int(11) DEFAULT NULL COMMENT '可以作为排名的一个重要因子,是通过积极完成平台的相关操作，所赚取的；\n            如登陆(2)，发布邀约计划（5)，上传相片(5)、回复(2)、举报有效(10), 建议（5)，建议采纳（50），被关注（1），照片被关注（1)，留言（2）',
  `MA_SINCERITY` int(11) DEFAULT NULL COMMENT '用来标识该会员的交友、伴 游诚意指数，越高越能标榜真诚\n            手机验证：+50\n            邮箱验证：+30\n            视频认证：+100\n            身份验证：+100\n            健康认证：+100',
  `MA_ONLINE` tinyint(4) DEFAULT NULL COMMENT '0：离线，1:在线',
  `MA_STATUS` tinyint(4) DEFAULT NULL COMMENT '0：正常，1：锁定(不让登陆），2：被举报，3：停用(该状态下会员资料会从页面下架)',
  `MA_MF_STATUS` tinyint(4) DEFAULT NULL COMMENT '0:暂停交友:会员资料会从系统下架\n            1:正在交友\n            2:强制下架；',
  `MA_AUTHENTICATED` tinyint(4) DEFAULT NULL COMMENT '由8位二进制组成：高两位为预留值0，每位上0代表没有认证，1为已认证，\n            00654321\n            1位：代表邮箱认证与否\n            2位：代表手机认证与否\n            3位：代表视频认证与否\n            4位：代表健康认证与否\n            5位：代表身份认证与否\n            6位：代表导游认证与否',
  `MA_GRADE` tinyint(4) DEFAULT NULL COMMENT '0:普通\n            1:蓝钻\n            2:黄钻\n            3:皇冠',
  `MA_FOCUS_COUNT` int(11) DEFAULT NULL COMMENT '点开个人详情就算被关注一下；',
  `MA_ORDER_WEIGHT` int(11) DEFAULT NULL COMMENT '值越大，权重越高，排名越靠前\n            根据订购\n             增值服务，会员级别，诚意度、积分数，资料完整度 共五项，依序算法来综合设定该值',
  `MA_IP_ADDR` varchar(20) DEFAULT NULL,
  `MA_TAGS` varchar(50) DEFAULT NULL COMMENT '多个词组成的字符串，词间用‘，’隔开',
  `MA_COMPLETED_PERCENT` tinyint(4) DEFAULT NULL COMMENT '100分制\n            对应到基本资料(10)，自荐信(10)，性格(5)，愿意偿试(5)、个人爱好(5)，伴游偏好(5)、伴游信息(10),职业信息（5），形体信息（10），个人相册(10)，形象照(10)、QQ(5)，手机(5)、邮箱(5)几个方面每个方面所当权限相异；',
  `MA_COMPLETED_INFO` int(11) DEFAULT NULL COMMENT '是一个整数，但是采用提二进制形式，总共由22位\n            00000000 00000000 00000000 00000000\n            低25位的0或1,分别代表以下各值填写的(由上而下对应二进制从低到高）资料是否完善:\n            呢称，\n            生日，\n            性别，\n            居住地，\n            自荐信，\n            驾龄,\n            体型自评，\n            愿偿试交友类型、\n            个人爱好，\n            伴游类型、\n            伴游经验,\n            职业信息，\n            形体信息，\n            个人相册，\n            形象照、\n            QQ、\n            手机、\n            邮箱，\n            微信,\n            最吸引人特点\n            愿去目的地\n            语言能力\n            ',
  `MA_POPULARIZE_ID` varchar(32) DEFAULT NULL,
  `MA_HEAD_ICON` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`MA_LOGIN_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `yaw_member_account` */

insert  into `yaw_member_account`(`MA_LOGIN_NAME`,`MA_SERVICE_ID`,`MA_PASSWORD`,`MA_TYPE`,`MA_REGIST_TIME`,`MA_LOGIN_TIME`,`MA_LOGIN_IP`,`MA_ONLINE_LONG`,`MA_YA_COIN`,`MA_EMAIL`,`MA_POINTS`,`MA_SINCERITY`,`MA_ONLINE`,`MA_STATUS`,`MA_MF_STATUS`,`MA_AUTHENTICATED`,`MA_GRADE`,`MA_FOCUS_COUNT`,`MA_ORDER_WEIGHT`,`MA_IP_ADDR`,`MA_TAGS`,`MA_COMPLETED_PERCENT`,`MA_COMPLETED_INFO`,`MA_POPULARIZE_ID`,`MA_HEAD_ICON`) values ('admin',NULL,'091107cea97e86fe37af0687eb8aa2d1',1,NULL,'2015-08-23 17:54:26','127.0.0.1',34,706,'13378917838@189.com',174,0,1,0,0,48,52,0,76,NULL,NULL,15,491520,NULL,NULL),('mng',NULL,'091107cea97e86fe37af0687eb8aa2d1',1,NULL,'2015-08-22 16:10:02','127.0.0.1',45,2210,'13378917838@189.com',254,780,1,0,1,0,0,4,223,NULL,NULL,71,8364027,NULL,NULL),('小朋友',NULL,'091107cea97e86fe37af0687eb8aa2d1',1,NULL,'2015-08-17 16:10:41','127.0.0.1',0,10,NULL,4,0,1,0,1,0,0,0,0,NULL,NULL,0,0,NULL,NULL),('爱人与人',NULL,'091107cea97e86fe37af0687eb8aa2d1',0,NULL,'2015-08-17 15:41:47','127.0.0.1',0,10,NULL,0,0,1,0,1,0,0,0,0,NULL,NULL,0,0,NULL,NULL),('爱吹NB的兔子',NULL,'091107cea97e86fe37af0687eb8aa2d1',0,NULL,'2015-08-17 16:20:00','127.0.0.1',0,10,NULL,134,0,1,0,1,0,0,5,0,NULL,NULL,0,0,NULL,NULL),('男人与女人',NULL,'091107cea97e86fe37af0687eb8aa2d1',0,'2015-08-20 19:41:12','2015-08-20 19:41:12','127.0.0.1',0,10,NULL,0,0,1,0,1,0,0,0,0,NULL,NULL,0,0,NULL,NULL);

/*Table structure for table `yaw_member_focus` */

DROP TABLE IF EXISTS `yaw_member_focus`;

CREATE TABLE `yaw_member_focus` (
  `FOCUS_ID` int(11) NOT NULL AUTO_INCREMENT,
  `FOCUS_MID` varchar(32) DEFAULT NULL,
  `FOCUS_BEFOCUS_MID` varchar(32) DEFAULT NULL COMMENT '该ID值的指向，取决于关注类型，如果关注是会员，则指的会员帐号；如果是相片，则批向相片主键，如果约请计划，则指向的是约请计划的主键',
  `FOCUS_DATE` datetime DEFAULT NULL,
  `FOCUS_TYPE` tinyint(4) DEFAULT NULL COMMENT '0:会员\n            2:相片\n            3:约请计划',
  `FOCUS_COUNT` int(11) DEFAULT NULL COMMENT '可设为增值服务项',
  PRIMARY KEY (`FOCUS_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='哪些用户今天在什么时候关注了会员的什么资料';

/*Data for the table `yaw_member_focus` */

insert  into `yaw_member_focus`(`FOCUS_ID`,`FOCUS_MID`,`FOCUS_BEFOCUS_MID`,`FOCUS_DATE`,`FOCUS_TYPE`,`FOCUS_COUNT`) values (1,NULL,'mng','2015-08-20 16:40:08',0,11),(2,NULL,'爱吹NB的兔子','2015-08-20 16:56:27',0,12);

/*Table structure for table `yaw_message` */

DROP TABLE IF EXISTS `yaw_message`;

CREATE TABLE `yaw_message` (
  `MSG_ID` int(11) NOT NULL AUTO_INCREMENT,
  `MSG_MID` varchar(32) DEFAULT NULL,
  `MSG_TIME` datetime DEFAULT NULL,
  `MSG_CONTENT` varchar(500) DEFAULT NULL,
  `MSG_REPLAY_MID` varchar(32) DEFAULT NULL,
  `MSG_REPLY_CONTENT` varchar(500) DEFAULT NULL,
  `MSG_REPLY_TIME` datetime DEFAULT NULL,
  `MSG_STATUS` tinyint(4) DEFAULT NULL COMMENT '0:留言未回复\n            1:留言已回复\n            2: 不理留言',
  `MSG_IS_SYSTEM` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`MSG_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

/*Data for the table `yaw_message` */

insert  into `yaw_message`(`MSG_ID`,`MSG_MID`,`MSG_TIME`,`MSG_CONTENT`,`MSG_REPLAY_MID`,`MSG_REPLY_CONTENT`,`MSG_REPLY_TIME`,`MSG_STATUS`,`MSG_IS_SYSTEM`) values (1,'admin',NULL,'听说你要出去了？！','爱吹NB的兔子','是的，怎么的，你也要一起去?','2015-08-17 15:02:41',1,0),(2,'admin','2015-08-17 17:22:29','再试一下看看','小朋友','再试一下看看','2015-08-17 17:23:49',2,0);

/*Table structure for table `yaw_my_friend` */

DROP TABLE IF EXISTS `yaw_my_friend`;

CREATE TABLE `yaw_my_friend` (
  `FRIEND_ID` int(11) NOT NULL AUTO_INCREMENT,
  `FRIEND_MY_ID` varchar(32) DEFAULT NULL,
  `FRIEND_FRIEND_ID` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`FRIEND_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `yaw_my_friend` */

/*Table structure for table `yaw_order` */

DROP TABLE IF EXISTS `yaw_order`;

CREATE TABLE `yaw_order` (
  `ORDER_NO` varchar(32) NOT NULL COMMENT '产品代号-年月日时分秒毫秒(3位）如\n            LZ-20140908120945109',
  `ORDER_MID` varchar(32) DEFAULT NULL,
  `ORDER_INCSERVICE_ID` int(11) DEFAULT NULL,
  `ORDER_MNG_ID` varchar(32) DEFAULT NULL,
  `ORDER_SUBMIT_TIME` datetime NOT NULL,
  `ORDER_STATUS` tinyint(4) NOT NULL COMMENT '0：待付款\n            1：已付款\n            2：已取消\n            3：已过期\n            4：已删除\n            5:   已受理',
  `ORDER_TOTAL_MONEY` int(11) NOT NULL,
  `ORDER_INCSERVICE_NAME` varchar(20) DEFAULT NULL COMMENT '0:    充约啊币 \n            10:   游客蓝钻\n            11：伴游蓝钻\n            20：伴游黄钻\n            30：游客皇冠\n            31：伴游皇冠\n            4:     置顶\n            5：  首页头条\n            6：  公开联系方式\n            7：  查看联系方式',
  `ORDER_COUNT` int(11) DEFAULT NULL,
  `ORDER_PRICE` int(11) DEFAULT NULL,
  `ORDER_PAY_MODE` tinyint(4) DEFAULT NULL COMMENT '1:支付平台\n            2:柜台汇款\n            3:网银转帐\n            0:约啊币付',
  `ORDER_PAY_ORG` varchar(20) DEFAULT NULL,
  `ORDER_PAY_TIME` datetime DEFAULT NULL,
  `ORDER_HANDLE_TIME` datetime DEFAULT NULL,
  PRIMARY KEY (`ORDER_NO`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='存储用户下单购买的流水';

/*Data for the table `yaw_order` */

insert  into `yaw_order`(`ORDER_NO`,`ORDER_MID`,`ORDER_INCSERVICE_ID`,`ORDER_MNG_ID`,`ORDER_SUBMIT_TIME`,`ORDER_STATUS`,`ORDER_TOTAL_MONEY`,`ORDER_INCSERVICE_NAME`,`ORDER_COUNT`,`ORDER_PRICE`,`ORDER_PAY_MODE`,`ORDER_PAY_ORG`,`ORDER_PAY_TIME`,`ORDER_HANDLE_TIME`) values ('20150723053731150','admin',1,'0','2015-07-23 17:37:31',1,1,'约啊币',1,NULL,0,NULL,NULL,NULL),('20150723054604791','admin',1,'0','2015-07-23 17:46:04',1,1,'约啊币',1,NULL,0,NULL,'2015-07-23 17:46:04',NULL),('20150723054659766','admin',1,'0','2015-07-23 17:46:59',1,1,'约啊币',1,NULL,0,NULL,'2015-07-23 17:46:59',NULL),('20150723055229337','admin',1,'0','2015-07-23 17:52:29',1,1,'约啊币',1,NULL,0,'约啊网','2015-07-23 17:52:29',NULL),('20150724025040572','admin',1,'0','2015-07-24 14:50:45',1,200,'约啊币',200,1,1,'支付宝','2015-08-13 20:18:57','2015-07-28 11:37:32'),('20150724102650270','admin',2,'0','2015-07-24 10:26:51',5,2,'VIP会员',1,200,-1,NULL,NULL,'2015-07-28 15:35:32'),('20150821021926703','mng',1,NULL,'2015-08-21 14:19:29',5,2000,'约啊币',2000,1,1,'支付宝','2015-08-21 14:25:42','2015-08-21 15:09:08'),('20150821041131459','mng',3,NULL,'2015-08-21 16:11:31',1,500,'伴游之首',1,500,1,'支付宝','2015-08-21 16:18:48',NULL),('20150821043325047','mng',1,NULL,'2015-08-21 16:33:25',1,1,'约啊币',1,0,0,'约啊网','2015-08-21 16:33:25',NULL),('20150821044728688','mng',4,'admin','2015-08-21 16:47:28',5,300,'蓝钻会员',1,0,1,'支付宝','2015-08-21 16:48:43','2015-08-21 16:49:33'),('20150822041303459','mng',3,NULL,'2015-08-22 16:13:03',1,500,'伴游之首',1,500,0,'约啊网','2015-08-22 16:21:12',NULL),('20150822043145809','admin',1,'admin','2015-08-22 16:31:45',5,1000,'约啊币',1000,1,2,'农业银行','2015-08-22 16:38:09','2015-08-22 16:39:19'),('20150822045759709','admin',1,'admin','2015-08-22 16:57:59',5,1000,'约啊币',1000,1,2,'中信业银行','2015-08-22 16:58:45','2015-08-22 16:59:16'),('20150822050101459','admin',4,NULL,'2015-08-22 17:01:01',1,300,'蓝钻会员',1,300,0,'约啊网','2015-08-22 17:01:42',NULL);

/*Table structure for table `yaw_photo` */

DROP TABLE IF EXISTS `yaw_photo`;

CREATE TABLE `yaw_photo` (
  `PHOTO_ID` int(11) NOT NULL AUTO_INCREMENT,
  `PHOTO_TYPE` int(11) DEFAULT NULL COMMENT '0:普通照\n            1:生活照\n            2:素颜照\n            3:艺术照\n            4:身份证\n            5:健康证\n            6:导游证\n            7:形像照\n            ',
  `PHOTO_MID` varchar(32) DEFAULT NULL,
  `PHOTO_FOCUS_COUNT` int(11) DEFAULT NULL COMMENT '被点击看详情一次，视为被关注一次',
  `PHOTO_URL` varchar(255) DEFAULT NULL COMMENT '原始图片的地址URL;\n            缩略图的地址规则为：\n            \n            原始图片的地址URL_规格号',
  `PHOTO_TITLE` varchar(50) DEFAULT NULL,
  `PHOTO_FORMAT` varchar(5) DEFAULT NULL,
  `PHOTO_STATUS` tinyint(4) DEFAULT NULL COMMENT '0：未认证\n            1：已认证',
  PRIMARY KEY (`PHOTO_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='所有原生照片信息都保存在这,其中伴游及游客的形象照在这里冗余一份;';

/*Data for the table `yaw_photo` */

insert  into `yaw_photo`(`PHOTO_ID`,`PHOTO_TYPE`,`PHOTO_MID`,`PHOTO_FOCUS_COUNT`,`PHOTO_URL`,`PHOTO_TITLE`,`PHOTO_FORMAT`,`PHOTO_STATUS`) values (1,1,'mng',2,'/test.jpg','我的样子，你喜欢','jpg',0);

/*Table structure for table `yaw_photo_thumbnail` */

DROP TABLE IF EXISTS `yaw_photo_thumbnail`;

CREATE TABLE `yaw_photo_thumbnail` (
  `THUMB_ID` int(11) NOT NULL AUTO_INCREMENT,
  `PHOTO_ID` int(11) DEFAULT NULL,
  `THUMB_SIZE` tinyint(4) DEFAULT NULL COMMENT '1:列表规格\n            2:头像规格\n            3:那什么再定',
  `THUMB_URL` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`THUMB_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='存储相片的其他规格的缩略图URL，原始规格的图片URL是存放于PHOTE、ESCORT、TOURIST表中';

/*Data for the table `yaw_photo_thumbnail` */

/*Table structure for table `yaw_popularize_record` */

DROP TABLE IF EXISTS `yaw_popularize_record`;

CREATE TABLE `yaw_popularize_record` (
  `POPULARIZE_ID` int(11) NOT NULL AUTO_INCREMENT,
  `POPULARIZE_MID` varchar(32) DEFAULT NULL,
  `POPULARIZE_EFFECTIVE_TIME` datetime DEFAULT NULL,
  `POPULARIZE_TYPE` tinyint(4) DEFAULT NULL COMMENT '1:贴链接\n            2:分享到',
  `POPULARIZE_IS_REGISTED` tinyint(4) DEFAULT NULL,
  `POPULARIZE_URL` varchar(255) DEFAULT NULL,
  `POPULARIZE_SHARETO` varchar(10) DEFAULT NULL COMMENT '0：贴吧\n            1：微信\n            2：微',
  `POPULARIZE_IP` varchar(40) DEFAULT NULL,
  PRIMARY KEY (`POPULARIZE_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `yaw_popularize_record` */

/*Table structure for table `yaw_r_incservice_member` */

DROP TABLE IF EXISTS `yaw_r_incservice_member`;

CREATE TABLE `yaw_r_incservice_member` (
  `RIM_ID` int(11) NOT NULL AUTO_INCREMENT,
  `RIM_MID` varchar(32) DEFAULT NULL,
  `RIM_INCSERVICE_ID` int(11) DEFAULT NULL COMMENT '11：城市置顶\n            12：搜索置顶\n            13：分类置顶\n             2： 公开联系方式\n             3：首页头条',
  `RIM_START` date DEFAULT NULL,
  `RIM_LENGTH` int(11) DEFAULT NULL,
  `RIM_END` date DEFAULT NULL,
  `RIM_ORDER_ID` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`RIM_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

/*Data for the table `yaw_r_incservice_member` */

insert  into `yaw_r_incservice_member`(`RIM_ID`,`RIM_MID`,`RIM_INCSERVICE_ID`,`RIM_START`,`RIM_LENGTH`,`RIM_END`,`RIM_ORDER_ID`) values (1,'mng',1,'2015-08-21',1,'2015-08-22',NULL),(2,'mng',4,'2015-08-21',30,'2015-09-20',NULL),(3,'mng',3,'2015-08-22',30,'2015-09-21',NULL),(4,'admin',1,'2015-08-22',1,'2015-08-23',NULL),(5,'admin',4,'2015-08-22',30,'2015-09-21',NULL);

/*Table structure for table `yaw_r_tripplan_escort` */

DROP TABLE IF EXISTS `yaw_r_tripplan_escort`;

CREATE TABLE `yaw_r_tripplan_escort` (
  `RTE_ID` int(11) NOT NULL AUTO_INCREMENT,
  `RTE_TRIPPLAN_ID` int(11) DEFAULT NULL,
  `RTE_MID` varchar(32) DEFAULT NULL,
  `RTE_AUTO_MATCH` tinyint(4) DEFAULT NULL COMMENT '0：非自动\n            1：自动',
  `RTE_RECOMMEND` varchar(500) DEFAULT NULL,
  `RTE_TIME` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`RTE_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

/*Data for the table `yaw_r_tripplan_escort` */

insert  into `yaw_r_tripplan_escort`(`RTE_ID`,`RTE_TRIPPLAN_ID`,`RTE_MID`,`RTE_AUTO_MATCH`,`RTE_RECOMMEND`,`RTE_TIME`) values (1,1,'mng',0,'我是一只小小鸟，想要飞呀飞飞，却怎么也飞不高','2015-08-04 14:38:49'),(2,1,'mng',0,'我是一只小小鸟，想要飞呀飞飞，却怎么也飞不高','2015-08-04 14:39:32');

/*Table structure for table `yaw_report_suggest` */

DROP TABLE IF EXISTS `yaw_report_suggest`;

CREATE TABLE `yaw_report_suggest` (
  `RS_ID` int(11) NOT NULL AUTO_INCREMENT,
  `RS_REPORT_MID` varchar(32) DEFAULT NULL,
  `RS_BEREPORT_MID` varchar(32) DEFAULT NULL,
  `RS_DATE` datetime DEFAULT NULL,
  `RS_TYPE` tinyint(4) DEFAULT NULL COMMENT '0：骗财骗物\n            1：不是本人\n            2：年龄不符\n            3：资料不实\n            4：建议意见',
  `RS_CONTENT` varchar(500) DEFAULT NULL,
  `RS_HANDLED` tinyint(4) DEFAULT NULL COMMENT '0:未处理\n            1:已受理/已采纳\n            2:忽略',
  PRIMARY KEY (`RS_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

/*Data for the table `yaw_report_suggest` */

insert  into `yaw_report_suggest`(`RS_ID`,`RS_REPORT_MID`,`RS_BEREPORT_MID`,`RS_DATE`,`RS_TYPE`,`RS_CONTENT`,`RS_HANDLED`) values (1,'mng','小朋友','2015-08-13 15:56:06',0,'试一下举报可用否：）',0),(2,'mng','小朋友','2015-08-13 15:58:19',0,'试一下举报可用否：）',0),(3,'mng','小朋友','2015-08-13 15:59:30',0,'试一下举报可用否：）',0),(4,'mng',NULL,'2015-08-13 16:27:28',4,'试一下提建议可用否：）',0);

/*Table structure for table `yaw_tag_record` */

DROP TABLE IF EXISTS `yaw_tag_record`;

CREATE TABLE `yaw_tag_record` (
  `TAG_ID` int(11) NOT NULL AUTO_INCREMENT,
  `TAG_MID` varchar(32) DEFAULT NULL,
  `TAG_TAGED_MID` varchar(32) DEFAULT NULL,
  `TAG_DATE` datetime DEFAULT NULL,
  `TAG_CONTENT` varchar(30) DEFAULT NULL COMMENT '最多三个标，每个标最多四个字的词，多标用","隔开',
  PRIMARY KEY (`TAG_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='完成评价之功能，只是不做成评价那么俗';

/*Data for the table `yaw_tag_record` */

insert  into `yaw_tag_record`(`TAG_ID`,`TAG_MID`,`TAG_TAGED_MID`,`TAG_DATE`,`TAG_CONTENT`) values (1,'mng','admin','2015-08-13 16:38:04','小美女,'),(2,'mng','admin','2015-08-13 16:39:03','小美女,');

/*Table structure for table `yaw_tourist_info` */

DROP TABLE IF EXISTS `yaw_tourist_info`;

CREATE TABLE `yaw_tourist_info` (
  `TOURIST_MID` varchar(32) NOT NULL,
  `TOURIST_NICKNAME` varchar(20) DEFAULT NULL,
  `TOURIST_IMAGE` varchar(255) DEFAULT NULL,
  `TOURIST_NAME` varchar(20) DEFAULT NULL,
  `TOURIST_BIRTHDAY` date DEFAULT NULL,
  `TOURIST_SEX` varchar(1) DEFAULT NULL,
  `TOURIST_LIVE_ADDR` varchar(100) DEFAULT NULL,
  `TOURIST_QQ` varchar(20) DEFAULT NULL,
  `TOURIST_WECHAT` varchar(20) DEFAULT NULL,
  `TOURIST_PHONE` varchar(20) DEFAULT NULL,
  `TOURIST_MOST_PRICE` int(11) DEFAULT NULL COMMENT 'n：元/天\n            -1：面议\n            0:未填',
  `TOURIST_MESSAGE` varchar(500) DEFAULT NULL,
  `TOURIST_LIKE_BODY` tinyint(4) DEFAULT NULL,
  `TOURIST_LIKE_IMAGE` tinyint(4) DEFAULT NULL,
  `TOURIST_LIKE_FEEL` tinyint(4) DEFAULT NULL,
  `TOURIST_LIKE_WEIGHT` tinyint(4) DEFAULT NULL,
  `TOURIST_LIKE_HEIGHT` int(11) DEFAULT NULL,
  `TOURIST_TRYTO` varchar(100) DEFAULT NULL,
  `TOURIST_LOVE` varchar(100) DEFAULT NULL,
  `TOURIST_ORDER_WEIGHT` int(11) DEFAULT NULL COMMENT '值越大，权重越高，排名越靠前\n            根据订购\n             增值服务，会员级别，诚意度、积分数，资料完整度 共五项，依序算法来综合设定该值',
  PRIMARY KEY (`TOURIST_MID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `yaw_tourist_info` */

insert  into `yaw_tourist_info`(`TOURIST_MID`,`TOURIST_NICKNAME`,`TOURIST_IMAGE`,`TOURIST_NAME`,`TOURIST_BIRTHDAY`,`TOURIST_SEX`,`TOURIST_LIVE_ADDR`,`TOURIST_QQ`,`TOURIST_WECHAT`,`TOURIST_PHONE`,`TOURIST_MOST_PRICE`,`TOURIST_MESSAGE`,`TOURIST_LIKE_BODY`,`TOURIST_LIKE_IMAGE`,`TOURIST_LIKE_FEEL`,`TOURIST_LIKE_WEIGHT`,`TOURIST_LIKE_HEIGHT`,`TOURIST_TRYTO`,`TOURIST_LOVE`,`TOURIST_ORDER_WEIGHT`) values ('爱人与人','爱人与人',NULL,NULL,NULL,'男',NULL,NULL,NULL,NULL,0,NULL,0,0,0,0,0,NULL,NULL,0),('爱吹NB的兔子',NULL,NULL,NULL,NULL,'男',NULL,NULL,NULL,NULL,0,NULL,0,0,0,0,0,NULL,NULL,0),('男人与女人','男人与女人',NULL,NULL,NULL,'男',NULL,NULL,NULL,NULL,0,NULL,0,0,0,0,0,NULL,NULL,0);

/*Table structure for table `yaw_tripplan` */

DROP TABLE IF EXISTS `yaw_tripplan`;

CREATE TABLE `yaw_tripplan` (
  `TRIPPLAN_ID` int(11) NOT NULL AUTO_INCREMENT,
  `TRIPPLAN_MID` varchar(32) DEFAULT NULL,
  `TRIPPLAN_STATUS` tinyint(4) DEFAULT NULL COMMENT '0:发布中\n            1:已完成\n            2:已过期\n            3;已取消',
  `TRIPPLAN_TITLE` varchar(50) DEFAULT NULL,
  `TRIPPLAN_MONEY_MODEL` tinyint(4) DEFAULT NULL COMMENT '0：费用全包，有酬金\n            1：费用全包，无酬金\n            2：费用AA制，结伴旅游\n            ',
  `TRIPPLAN_TYPE` tinyint(4) DEFAULT NULL COMMENT '0：纯陪玩\n            1：景区游\n            2：城市游',
  `TRIPPLAN_PUBLISH_TIME` datetime DEFAULT NULL,
  `TRIPPLAN_DEPART_TIME` datetime DEFAULT NULL,
  `TRIPPLAN_DEPART_ADDR` varchar(20) DEFAULT NULL,
  `TRIPPLAN_DESTINATION` varchar(20) DEFAULT NULL,
  `TRIPPLAN_DAY` tinyint(4) DEFAULT NULL COMMENT '住一晚，算一天，否则算半天',
  `TRIPPLAN_WANT_SEX` varchar(1) DEFAULT NULL,
  `TRIPPLAN_WANT_AGE` tinyint(4) DEFAULT NULL,
  `TRIPPLAN_WANT_PERSONS` tinyint(4) DEFAULT '1',
  `TRIPPLAN_OTHER` varchar(500) DEFAULT NULL,
  `TRIPPLAN_ORDER_WEIGHT` int(11) DEFAULT NULL COMMENT '值越大，权重越高，排名越靠前\n            权重因子：关注度(50%)+会员排名权重(50%)',
  `TRIPPLAN_BEFOCUS_COUNT` int(11) DEFAULT NULL,
  PRIMARY KEY (`TRIPPLAN_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

/*Data for the table `yaw_tripplan` */

insert  into `yaw_tripplan`(`TRIPPLAN_ID`,`TRIPPLAN_MID`,`TRIPPLAN_STATUS`,`TRIPPLAN_TITLE`,`TRIPPLAN_MONEY_MODEL`,`TRIPPLAN_TYPE`,`TRIPPLAN_PUBLISH_TIME`,`TRIPPLAN_DEPART_TIME`,`TRIPPLAN_DEPART_ADDR`,`TRIPPLAN_DESTINATION`,`TRIPPLAN_DAY`,`TRIPPLAN_WANT_SEX`,`TRIPPLAN_WANT_AGE`,`TRIPPLAN_WANT_PERSONS`,`TRIPPLAN_OTHER`,`TRIPPLAN_ORDER_WEIGHT`,`TRIPPLAN_BEFOCUS_COUNT`) values (1,'爱吹NB的兔子',0,'北京三日AA游',2,2,'2015-08-04 14:29:06','2015-08-22 00:00:00','长沙','北京',3,'女',21,2,'无不良嗜好',175,2),(2,'爱吹NB的兔子',0,'北京三日AA游1',2,2,'2015-08-04 14:51:28','2015-08-22 00:00:00','长沙1','北京',3,'女',21,2,'无不良嗜好',175,0),(3,'爱吹NB的兔子',0,'北京三日AA游2',2,2,'2015-08-04 14:51:42','2015-08-22 00:00:00','长沙1','北京',3,'女',21,2,'无不良嗜好',175,0),(4,'爱吹NB的兔子',0,'北京三日AA游3',2,2,'2015-08-04 14:51:48','2015-08-22 00:00:00','长沙1','北京',3,'女',21,2,'无不良嗜好',175,0),(5,'爱吹NB的兔子',0,'北京三日AA游4',2,2,'2015-08-04 14:51:53','2015-08-22 00:00:00','长沙1','北京',3,'女',21,2,'无不良嗜好',175,0),(6,'爱吹NB的兔子',0,'北京三日AA游5',2,2,'2015-08-04 14:51:59','2015-08-22 00:00:00','长沙1','北京',3,'女',21,2,'无不良嗜好',175,0),(7,'爱吹NB的兔子',0,'北京三日AA游6',2,2,'2015-08-04 14:52:08','2015-08-22 00:00:00','长沙1','北京',3,'女',21,2,'无不良嗜好',175,0);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
