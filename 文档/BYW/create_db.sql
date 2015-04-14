/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     2015/4/7 16:51:46                            */
/*==============================================================*/


drop table if exists YAW_APPLY_AUTHENTICATION;

drop table if exists YAW_CHANGE_RECORD;

drop table if exists YAW_ESCORT_INFO;

drop table if exists YAW_INCREMENT_SERVICE;

drop table if exists YAW_LOGIN_LOG;

drop table if exists YAW_MANAGER_ACCOUNT;

drop table if exists YAW_MEMBER_ACCOUNT;

drop table if exists YAW_MEMBER_FOCUS;

drop table if exists YAW_MESSAGE;

drop table if exists YAW_MY_FRIEND;

drop table if exists YAW_ORDER;

drop table if exists YAW_PHOTO;

drop table if exists YAW_PHOTO_THUMBNAIL;

drop table if exists YAW_POPULARIZE_RECORD;

drop table if exists YAW_REPORT_SUGGEST;

drop table if exists YAW_R_INCSERVICE_MEMBER;

drop table if exists YAW_R_TRIPPLAN_ESCORT;

drop table if exists YAW_TAG_RECORD;

drop table if exists YAW_TOURIST_INFO;

drop table if exists YAW_TRIPPLAN;

/*==============================================================*/
/* Table: YAW_APPLY_AUTHENTICATION                              */
/*==============================================================*/
create table YAW_APPLY_AUTHENTICATION
(
   AUTH_ID              int not null auto_increment,
   AUTH_MNG_ID          varchar(32),
   AUTH_MID             varchar(32),
   AUTH_DATE            datetime,
   AUTH_TYPE            tinyint comment '1：视频认证
            2：身份认证
            3：导游认证
            4：健康认证
            5：加入伴游俱乐部申请',
   AUTH_RESULT          tinyint,
   AUTH_RESON           varchar(200),
   AUTH_AUDIT_TIME      datetime,
   primary key (AUTH_ID)
);

alter table YAW_APPLY_AUTHENTICATION comment '会员申请视频,身份,健康等认证申请记录表';

/*==============================================================*/
/* Table: YAW_CHANGE_RECORD                                     */
/*==============================================================*/
create table YAW_CHANGE_RECORD
(
   变更ID                 int not null auto_increment,
   MA_LOGIN_NAME        varchar(32),
   变更类型                 char(10),
   变更时间                 char(10),
   变更内容                 char(10),
   primary key (变更ID)
);

alter table YAW_CHANGE_RECORD comment '存储会员资料、相册，形象，联系方式等方面的变更';

/*==============================================================*/
/* Table: YAW_ESCORT_INFO                                       */
/*==============================================================*/
create table YAW_ESCORT_INFO
(
   ESCORT_MID           varchar(32) not null,
   ESCORT_NAME          varchar(30),
   ESCORT_NICK_NAME     varchar(30),
   ESCORT_BIRTHDAY      date,
   ESCORT_SEX           varchar(1),
   ESCORT_LIVE_ADDR     varchar(20),
   ESCORT_DRIVE_YEAR    int default 0,
   ESCORT_BODY          tinyint comment '0:  未填写
            1：苗条
            2：GU感
            3：肉感
            4：丰满
            5：火辣
            6：匀称',
   ESCORT_WEIGHT        tinyint,
   ESCORT_HEIGHT        int,
   ESCORT_IMAGE         tinyint comment '0:   未填
            1：清秀
            2：阳光
            3：可爱
            4：甜美
            5:性感',
   ESCORT_FEEL          tinyint comment '0：知性
            1：性感
            2：妩媚
            3：妖艳
            4：中性
            ',
   ESCORT_FACE_PIC      varchar(255),
   ESCORT_LOVE          varchar(100),
   ESCORT_JOB           tinyint comment '0:未填写
            1:教育\培训
            2
            3
            4
            ',
   ESCORT_QQ            varchar(20),
   ESCORT_WECHAT        varchar(20),
   ESCORT_PHONE         varchar(20),
   ESCORT_PRICE         int default 0 comment 'n:元/天
            -1：面议',
   ESCORT_RECOMMEND     varchar(500),
   ESCORT_TYPE          tinyint comment '0：未填定
            1：私人陪游
            2：交友私会
            3：向导导游
            4：交友蹭游
            5：商务秘书',
   ESCORT_EXP           tinyint comment '1：职业伴游
            2：有过几次
            3：没有经验
            4:   执证导游
            0:  未填写
            ',
   ESCORT_TRYTO         varchar(100),
   ESCORT_LANGUAGE      varchar(10),
   ESCORT_ATTRACTIVE    varchar(5),
   ESCORT_TRIP_ADDR     varchar(20) comment '没填，就是哪都可以。',
   ESCORT_CLUB_MEMBER   tinyint comment '0:不是
            1:是',
   ESCORT_ORDER_WEIGHT  int comment '值越大，权重越高，排名越靠前
            根据订购
             增值服务，会员级别，诚意度、积分数，资料完整度 共五项，依序算法来综合设定该值',
   primary key (ESCORT_MID)
);

/*==============================================================*/
/* Table: YAW_INCREMENT_SERVICE                                 */
/*==============================================================*/
create table YAW_INCREMENT_SERVICE
(
   INCSERVICE_ID        int not null auto_increment,
   INCSERVICE_NAME      varchar(20),
   INCSERVICE_SUIT_DAY  int,
   INCSERVICE_PRICE     int,
   primary key (INCSERVICE_ID)
);

alter table YAW_INCREMENT_SERVICE comment '存储平台的几种增值服务项目；该表暂时先预给定数据,后面再提供管理操作功能;
约啊币本身是一种增值服务，单价一';

/*==============================================================*/
/* Table: YAW_LOGIN_LOG                                         */
/*==============================================================*/
create table YAW_LOGIN_LOG
(
   LOGIN_ID             int not null,
   LOGIN_MID            varchar(32),
   LOGIN_DATE           datetime,
   LOGIN_IP             varchar(40),
   LOGIN_ISPHONE        tinyint comment '0:不是
            1:是',
   LOGIN_LENGTH         int,
   LOGIN_IP_ADDR        varchar(20),
   primary key (LOGIN_ID)
);

/*==============================================================*/
/* Table: YAW_MANAGER_ACCOUNT                                   */
/*==============================================================*/
create table YAW_MANAGER_ACCOUNT
(
   MNG_LOGIN_NAME       varchar(32) not null,
   MNG_PASSWORD         varchar(32),
   MNG_NAME             varchar(16),
   MNG_TYPE             tinyint comment '0:客服
            1:管理员
            2:超级管理员',
   MNG_LOGIN_TIME       datetime,
   MNG_LOGIN_IP         varchar(64),
   MNG_LOGIN_LENGTH     int,
   MNG_STATUS           tinyint comment '是否锁定；1：正常，2：锁定
            ',
   MNG_ONLINE           tinyint comment '0：离线，1:在线',
   primary key (MNG_LOGIN_NAME)
);

/*==============================================================*/
/* Table: YAW_MEMBER_ACCOUNT                                    */
/*==============================================================*/
create table YAW_MEMBER_ACCOUNT
(
   MA_LOGIN_NAME        varchar(32) not null,
   MA_SERVICE_ID        varchar(100) comment '一些外部服务或设备的唯标识号',
   MA_PASSWORD          varchar(32) not null,
   MA_TYPE              tinyint comment '0:游客
            1:伴游
            ',
   MA_REGIST_TIME       datetime,
   MA_LOGIN_TIME        datetime,
   MA_LOGIN_IP          varchar(40),
   MA_ONLINE_LONG       int,
   MA_YA_COIN           int comment '可以当人民币用，是买来的或推广、分享赚来的',
   MA_EMAIL             varchar(50),
   MA_POINTS            int comment '可以作为排名的一个重要因子,是通过积极完成平台的相关操作，所赚取的；
            如登陆(2)，发布邀约计划（5)，上传相片(5)、回复(2)、举报有效(10), 建议（5)，建议采纳（50），被关注（1），照片被关注（1)，留言（2）',
   MA_SINCERITY         tinyint comment '用来标识该会员的交友、伴 游诚意指数，越高越能标榜真诚
            手机验证：+50
            邮箱验证：+30
            视频认证：+100
            身份验证：+100
            健康认证：+100',
   MA_ONLINE            tinyint comment '0：离线，1:在线',
   MA_STATUS            tinyint comment '0：正常，1：锁定(不让登陆），2：被举报，3：停用(该状态下会员资料会从页面下架)',
   MA_MF_STATUS         tinyint comment '0:暂停交友:会员资料会从系统下架
            1:正在交友
            2:强制下架；',
   MA_AUTHENTICATED     tinyint comment '由8位二进制组成：高两位为预留值0，每位上0代表没有认证，1为已认证，
            00654321
            1位：代表邮箱认证与否
            2位：代表手机认证与否
            3位：代表视频认证与否
            4位：代表健康认证与否
            5位：代表身份认证与否
            6位：代表导游认证与否',
   MA_GRADE             int comment '0:普通
            1:蓝钻
            2:黄钻
            3:皇冠',
   MA_FOCUS_COUNT       int comment '点开个人详情就算被关注一下；',
   MA_ORDER_WEIGHT      int comment '值越大，权重越高，排名越靠前
            根据订购
             增值服务，会员级别，诚意度、积分数，资料完整度 共五项，依序算法来综合设定该值',
   MA_IP_ADDR           varchar(20),
   MA_TAGS              varchar(50) comment '多个词组成的字符串，词间用‘，’隔开',
   MA_COMPLETED_PERCENT tinyint comment '100分制
            对应到基本资料(10)，自荐信(10)，性格(5)，愿意偿试(5)、个人爱好(5)，伴游偏好(5)、伴游信息(10),职业信息（5），形体信息（10），个人相册(10)，形象照(10)、QQ(5)，手机(5)、邮箱(5)几个方面每个方面所当权限相异；',
   MA_COMPLETED_INFO    int comment '是一个整数，但是采用提二进制形式，总共由22位
            00000000 00000000 00000000 00000000
            低25位的0或1,分别代表以下各值填写的(由上而下对应二进制从低到高）资料是否完善:
            呢称，
            生日，
            性别，
            居住地，
            自荐信，
            驾龄,
            体型自评，
            愿偿试交友类型、
            个人爱好，
            伴游类型、
            伴游经验,
            职业信息，
            形体信息，
            个人相册，
            形象照、
            QQ、
            手机、
            邮箱，
            微信,
            最吸引人特点
            愿去目的地
            语言能力
            ',
   MA_POPULARIZE_ID     varchar(32),
   MA_HEAD_ICON         varchar(255),
   primary key (MA_LOGIN_NAME)
);

/*==============================================================*/
/* Table: YAW_MEMBER_FOCUS                                      */
/*==============================================================*/
create table YAW_MEMBER_FOCUS
(
   FOCUS_ID             int not null auto_increment,
   FOCUS_MID            varchar(32),
   FOCUS_BEFOCUS_OID    varchar(32) comment '该ID值的指向，取决于关注类型，如果关注是会员，则指的会员帐号；如果是相片，则批向相片主键，如果约请计划，则指向的是约请计划的主键',
   FOCUS_DATE           datetime,
   FOCUS_TYPE           tinyint comment '0:会员
            2:相片
            3:约请计划',
   FOCUS_COUNT          int comment '可设为增值服务项',
   primary key (FOCUS_ID)
);

alter table YAW_MEMBER_FOCUS comment '哪些用户今天在什么时候关注了会员的什么资料';

/*==============================================================*/
/* Table: YAW_MESSAGE                                           */
/*==============================================================*/
create table YAW_MESSAGE
(
   MSG_ID               int not null auto_increment,
   MSG_MID              varchar(32),
   MSG_TIME             datetime,
   MSG_CONTENT          varchar(500),
   MSG_REPLAY_MID       varchar(32),
   MSG_REPLY_CONTENT    varchar(500),
   MSG_REPLY_TIME       datetime,
   MSG_STATUS           tinyint comment '0:留言未回复
            1:留言已回复
            2: 不理留言',
   MSG_IS_SYSTEM        tinyint,
   primary key (MSG_ID)
);

/*==============================================================*/
/* Table: YAW_MY_FRIEND                                         */
/*==============================================================*/
create table YAW_MY_FRIEND
(
   FRIEND_ID            int not null auto_increment,
   FRIEND_MY_ID         varchar(32),
   FRIEND_FRIEND_ID     varchar(32),
   primary key (FRIEND_ID)
);

/*==============================================================*/
/* Table: YAW_ORDER                                             */
/*==============================================================*/
create table YAW_ORDER
(
   ORDER_NO             varchar(32) not null comment '产品代号-年月日时分秒毫秒(3位）如
            LZ-20140908120945109',
   ORDER_MID            varchar(32),
   ORDER_INCSERVICE_ID  int,
   ORDER_MNG_ID         varchar(32),
   ORDER_SUBMIT_TIME    datetime not null,
   ORDER_STATUS         tinyint not null comment '0：待付款
            1：已付款
            2：已取消
            3：已过期
            4：已删除
            5:   已受理',
   ORDER_TOTAL_MONEY    int not null,
   ORDER_INCSERVICE_NAME varchar(20) comment '0:    充约啊币 
            10:   游客蓝钻
            11：伴游蓝钻
            20：伴游黄钻
            30：游客皇冠
            31：伴游皇冠
            4:     置顶
            5：  首页头条
            6：  公开联系方式
            7：  查看联系方式',
   ORDER_COUNT          int,
   ORDER_PAY_MODE       tinyint comment '1:支付平台
            2:柜台汇款
            3:网银转帐
            0:约啊币付',
   ORDER_PAY_ORG        varchar(20),
   ORDER_PAY_TIME       datetime,
   ORDER_HANDLE_TIME    datetime,
   primary key (ORDER_NO)
);

alter table YAW_ORDER comment '存储用户下单购买的流水';

/*==============================================================*/
/* Table: YAW_PHOTO                                             */
/*==============================================================*/
create table YAW_PHOTO
(
   PHOTO_ID             int not null auto_increment,
   PHOTO_TYPE           int comment '0:普通照
            1:生活照
            2:素颜照
            3:艺术照
            4:身份证
            5:健康证
            6:导游证
            7:形像照
            ',
   PHOTO_MID            varchar(32),
   PHOTO_FOCUS_COUNT    int comment '被点击看详情一次，视为被关注一次',
   PHOTO_URL            varchar(255) comment '原始图片的地址URL;
            缩略图的地址规则为：
            
            原始图片的地址URL_规格号',
   PHOTO_TITLE          varchar(50),
   PHOTO_FORMAT         varchar(5),
   PHOTO_STATUS         tinyint comment '0：未认证
            1：已认证',
   primary key (PHOTO_ID)
);

alter table YAW_PHOTO comment '所有原生照片信息都保存在这,其中伴游及游客的形象照在这里冗余一份;';

/*==============================================================*/
/* Table: YAW_PHOTO_THUMBNAIL                                   */
/*==============================================================*/
create table YAW_PHOTO_THUMBNAIL
(
   THUMB_ID             int not null auto_increment,
   PHOTO_ID             int,
   THUMB_SIZE           tinyint comment '1:列表规格
            2:头像规格
            3:那什么再定',
   THUMB_URL            varchar(255),
   primary key (THUMB_ID)
);

alter table YAW_PHOTO_THUMBNAIL comment '存储相片的其他规格的缩略图URL，原始规格的图片URL是存放于PHOTE、ESCORT、TOURIST表中';

/*==============================================================*/
/* Table: YAW_POPULARIZE_RECORD                                 */
/*==============================================================*/
create table YAW_POPULARIZE_RECORD
(
   POPULARIZE_ID        int not null auto_increment,
   POPULARIZE_MID       varchar(32),
   POPULARIZE_EFFECTIVE_TIME datetime,
   POPULARIZE_TYPE      tinyint comment '1:贴链接
            2:分享到',
   POPULARIZE_IS_REGISTED tinyint,
   POPULARIZE_URL       varchar(255),
   POPULARIZE_SHARETO   varchar(10) comment '0：贴吧
            1：微信
            2：微',
   POPULARIZE_IP        varchar(40),
   primary key (POPULARIZE_ID)
);

/*==============================================================*/
/* Table: YAW_REPORT_SUGGEST                                    */
/*==============================================================*/
create table YAW_REPORT_SUGGEST
(
   RS_ID                int not null auto_increment,
   RS_REPORT_MID        varchar(32),
   RS_BEREPORT_MID      varchar(32),
   RS_DATE              datetime,
   RS_TYPE              tinyint comment '0：骗财骗物
            1：不是本人
            2：年龄不符
            3：资料不实
            4：建议意见',
   RS_CONTENT           varchar(500),
   RS_HANDLED           tinyint comment '0:未处理
            1:已受理/已采纳
            2:忽略',
   primary key (RS_ID)
);

/*==============================================================*/
/* Table: YAW_R_INCSERVICE_MEMBER                               */
/*==============================================================*/
create table YAW_R_INCSERVICE_MEMBER
(
   RIM_ID               int not null,
   RIM_MID              varchar(32),
   RIM_INCSERVICE_ID    int comment '11：城市置顶
            12：搜索置顶
            13：分类置顶
             2： 公开联系方式
             3：首页头条',
   RIM_START            date,
   RIM_LENGTH           int,
   RIM_END              date,
   primary key (RIM_ID)
);

/*==============================================================*/
/* Table: YAW_R_TRIPPLAN_ESCORT                                 */
/*==============================================================*/
create table YAW_R_TRIPPLAN_ESCORT
(
   RTE_ID               int not null auto_increment,
   RTE_TRIPPLAN_ID      int,
   RTE_MID              varchar(32),
   RTE_AUTO_MATCH       tinyint comment '0：非自动
            1：自动',
   RTE_RECOMMEND        varchar(500),
   RTE_TIME             timestamp,
   primary key (RTE_ID)
);

/*==============================================================*/
/* Table: YAW_TAG_RECORD                                        */
/*==============================================================*/
create table YAW_TAG_RECORD
(
   TAG_ID               int not null,
   TAG_MID              varchar(32),
   TAG_TAGED_MID        varchar(32),
   TAG_DATE             datetime,
   TAG_CONTENT          varchar(30) comment '最多三个标，每个标最多四个字的词，多标用","隔开',
   primary key (TAG_ID)
);

alter table YAW_TAG_RECORD comment '完成评价之功能，只是不做成评价那么俗';

/*==============================================================*/
/* Table: YAW_TOURIST_INFO                                      */
/*==============================================================*/
create table YAW_TOURIST_INFO
(
   TOURIST_MID          varchar(32) not null,
   TOURIST_NICKNAME     varchar(20),
   TOURIST_IMAGE        varchar(255),
   TOURIST_NAME         varchar(20),
   TOURIST_BIRTHDAY     date,
   TOURIST_SEX          varchar(1),
   TOURIST_LIVE_ADDR    varchar(100),
   TOURIST_QQ           varchar(20),
   TOURIST_WECHAT       varchar(20),
   TOURIST_PHONE        varchar(20),
   TOURIST_MOST_PRICE   int comment 'n：元/天
            -1：面议
            0:未填',
   TOURIST_MESSAGE      varchar(500),
   TOURIST_LIKE_BODY    tinyint,
   TOURIST_LIKE_IMAGE   tinyint,
   TOURIST_LIKE_FEEL    tinyint,
   TOURIST_LIKE_WEIGHT  tinyint,
   TOURIST_LIKE_HEIGHT  int,
   TOURIST_TRYTO        varchar(100),
   TOURIST_LOVE         varchar(100),
   TOURIST_ORDER_WEIGHT int comment '值越大，权重越高，排名越靠前
            根据订购
             增值服务，会员级别，诚意度、积分数，资料完整度 共五项，依序算法来综合设定该值',
   primary key (TOURIST_MID)
);

/*==============================================================*/
/* Table: YAW_TRIPPLAN                                          */
/*==============================================================*/
create table YAW_TRIPPLAN
(
   TRIPPLAN_ID          int not null auto_increment,
   TRIPPLAN_MID         varchar(32),
   TRIPPLAN_STATUS      tinyint comment '0:发布中
            1:已完成
            2:已过期
            3;已取消',
   TRIPPLAN_TITLE       varchar(50),
   TRIPPLAN_MONEY_MODEL tinyint comment '0：费用全包，有酬金
            1：费用全包，无酬金
            2：费用AA制，结伴旅游
            ',
   TRIPPLAN_TYPE        tinyint comment '0：纯陪玩
            1：景区游
            2：城市游',
   TRIPPLAN_PUBLISH_TIME datetime,
   TRIPPLAN_DEPART_TIME datetime,
   TRIPPLAN_DEPART_ADDR varchar(20),
   TRIPPLAN_DESTINATION varchar(20),
   TRIPPLAN_DAY         tinyint comment '住一晚，算一天，否则算半天',
   TRIPPLAN_WANT_SEX    varchar(1),
   TRIPPLAN_WANT_AGE    tinyint,
   TRIPPLAN_WANT_PERSONS tinyint default 1,
   TRIPPLAN_OTHER       varchar(500),
   TRIPPLAN_ORDER_WEIGHT int comment '值越大，权重越高，排名越靠前
            权重因子：关注度(50%)+会员排名权重(50%)',
   TRIPPLAN_BEFOCUS_COUNT int,
   primary key (TRIPPLAN_ID)
);

