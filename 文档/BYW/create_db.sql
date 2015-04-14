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
   AUTH_TYPE            tinyint comment '1����Ƶ��֤
            2�������֤
            3��������֤
            4��������֤
            5��������ξ��ֲ�����',
   AUTH_RESULT          tinyint,
   AUTH_RESON           varchar(200),
   AUTH_AUDIT_TIME      datetime,
   primary key (AUTH_ID)
);

alter table YAW_APPLY_AUTHENTICATION comment '��Ա������Ƶ,���,��������֤�����¼��';

/*==============================================================*/
/* Table: YAW_CHANGE_RECORD                                     */
/*==============================================================*/
create table YAW_CHANGE_RECORD
(
   ���ID                 int not null auto_increment,
   MA_LOGIN_NAME        varchar(32),
   �������                 char(10),
   ���ʱ��                 char(10),
   �������                 char(10),
   primary key (���ID)
);

alter table YAW_CHANGE_RECORD comment '�洢��Ա���ϡ���ᣬ������ϵ��ʽ�ȷ���ı��';

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
   ESCORT_BODY          tinyint comment '0:  δ��д
            1������
            2��GU��
            3�����
            4������
            5������
            6���ȳ�',
   ESCORT_WEIGHT        tinyint,
   ESCORT_HEIGHT        int,
   ESCORT_IMAGE         tinyint comment '0:   δ��
            1������
            2������
            3���ɰ�
            4������
            5:�Ը�',
   ESCORT_FEEL          tinyint comment '0��֪��
            1���Ը�
            2������
            3������
            4������
            ',
   ESCORT_FACE_PIC      varchar(255),
   ESCORT_LOVE          varchar(100),
   ESCORT_JOB           tinyint comment '0:δ��д
            1:����\��ѵ
            2
            3
            4
            ',
   ESCORT_QQ            varchar(20),
   ESCORT_WECHAT        varchar(20),
   ESCORT_PHONE         varchar(20),
   ESCORT_PRICE         int default 0 comment 'n:Ԫ/��
            -1������',
   ESCORT_RECOMMEND     varchar(500),
   ESCORT_TYPE          tinyint comment '0��δ�
            1��˽������
            2������˽��
            3���򵼵���
            4�����Ѳ���
            5����������',
   ESCORT_EXP           tinyint comment '1��ְҵ����
            2���й�����
            3��û�о���
            4:   ִ֤����
            0:  δ��д
            ',
   ESCORT_TRYTO         varchar(100),
   ESCORT_LANGUAGE      varchar(10),
   ESCORT_ATTRACTIVE    varchar(5),
   ESCORT_TRIP_ADDR     varchar(20) comment 'û������Ķ����ԡ�',
   ESCORT_CLUB_MEMBER   tinyint comment '0:����
            1:��',
   ESCORT_ORDER_WEIGHT  int comment 'ֵԽ��Ȩ��Խ�ߣ�����Խ��ǰ
            ���ݶ���
             ��ֵ���񣬻�Ա���𣬳���ȡ������������������� ����������㷨���ۺ��趨��ֵ',
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

alter table YAW_INCREMENT_SERVICE comment '�洢ƽ̨�ļ�����ֵ������Ŀ���ñ���ʱ��Ԥ��������,�������ṩ�����������;
Լ���ұ�����һ����ֵ���񣬵���һ';

/*==============================================================*/
/* Table: YAW_LOGIN_LOG                                         */
/*==============================================================*/
create table YAW_LOGIN_LOG
(
   LOGIN_ID             int not null,
   LOGIN_MID            varchar(32),
   LOGIN_DATE           datetime,
   LOGIN_IP             varchar(40),
   LOGIN_ISPHONE        tinyint comment '0:����
            1:��',
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
   MNG_TYPE             tinyint comment '0:�ͷ�
            1:����Ա
            2:��������Ա',
   MNG_LOGIN_TIME       datetime,
   MNG_LOGIN_IP         varchar(64),
   MNG_LOGIN_LENGTH     int,
   MNG_STATUS           tinyint comment '�Ƿ�������1��������2������
            ',
   MNG_ONLINE           tinyint comment '0�����ߣ�1:����',
   primary key (MNG_LOGIN_NAME)
);

/*==============================================================*/
/* Table: YAW_MEMBER_ACCOUNT                                    */
/*==============================================================*/
create table YAW_MEMBER_ACCOUNT
(
   MA_LOGIN_NAME        varchar(32) not null,
   MA_SERVICE_ID        varchar(100) comment 'һЩ�ⲿ������豸��Ψ��ʶ��',
   MA_PASSWORD          varchar(32) not null,
   MA_TYPE              tinyint comment '0:�ο�
            1:����
            ',
   MA_REGIST_TIME       datetime,
   MA_LOGIN_TIME        datetime,
   MA_LOGIN_IP          varchar(40),
   MA_ONLINE_LONG       int,
   MA_YA_COIN           int comment '���Ե�������ã��������Ļ��ƹ㡢����׬����',
   MA_EMAIL             varchar(50),
   MA_POINTS            int comment '������Ϊ������һ����Ҫ����,��ͨ���������ƽ̨����ز�������׬ȡ�ģ�
            ���½(2)��������Լ�ƻ���5)���ϴ���Ƭ(5)���ظ�(2)���ٱ���Ч(10), ���飨5)��������ɣ�50��������ע��1������Ƭ����ע��1)�����ԣ�2��',
   MA_SINCERITY         tinyint comment '������ʶ�û�Ա�Ľ��ѡ��� �γ���ָ����Խ��Խ�ܱ�����
            �ֻ���֤��+50
            ������֤��+30
            ��Ƶ��֤��+100
            �����֤��+100
            ������֤��+100',
   MA_ONLINE            tinyint comment '0�����ߣ�1:����',
   MA_STATUS            tinyint comment '0��������1������(���õ�½����2�����ٱ���3��ͣ��(��״̬�»�Ա���ϻ��ҳ���¼�)',
   MA_MF_STATUS         tinyint comment '0:��ͣ����:��Ա���ϻ��ϵͳ�¼�
            1:���ڽ���
            2:ǿ���¼ܣ�',
   MA_AUTHENTICATED     tinyint comment '��8λ��������ɣ�����λΪԤ��ֵ0��ÿλ��0����û����֤��1Ϊ����֤��
            00654321
            1λ������������֤���
            2λ�������ֻ���֤���
            3λ��������Ƶ��֤���
            4λ����������֤���
            5λ�����������֤���
            6λ����������֤���',
   MA_GRADE             int comment '0:��ͨ
            1:����
            2:����
            3:�ʹ�',
   MA_FOCUS_COUNT       int comment '�㿪����������㱻��עһ�£�',
   MA_ORDER_WEIGHT      int comment 'ֵԽ��Ȩ��Խ�ߣ�����Խ��ǰ
            ���ݶ���
             ��ֵ���񣬻�Ա���𣬳���ȡ������������������� ����������㷨���ۺ��趨��ֵ',
   MA_IP_ADDR           varchar(20),
   MA_TAGS              varchar(50) comment '�������ɵ��ַ������ʼ��á���������',
   MA_COMPLETED_PERCENT tinyint comment '100����
            ��Ӧ����������(10)���Լ���(10)���Ը�(5)��Ը�⳥��(5)�����˰���(5)������ƫ��(5)��������Ϣ(10),ְҵ��Ϣ��5����������Ϣ��10�����������(10)��������(10)��QQ(5)���ֻ�(5)������(5)��������ÿ����������Ȩ�����죻',
   MA_COMPLETED_INFO    int comment '��һ�����������ǲ������������ʽ���ܹ���22λ
            00000000 00000000 00000000 00000000
            ��25λ��0��1,�ֱ�������¸�ֵ��д��(���϶��¶�Ӧ�����ƴӵ͵��ߣ������Ƿ�����:
            �سƣ�
            ���գ�
            �Ա�
            ��ס�أ�
            �Լ��ţ�
            ����,
            ����������
            Ը���Խ������͡�
            ���˰��ã�
            �������͡�
            ���ξ���,
            ְҵ��Ϣ��
            ������Ϣ��
            ������ᣬ
            �����ա�
            QQ��
            �ֻ���
            ���䣬
            ΢��,
            ���������ص�
            ԸȥĿ�ĵ�
            ��������
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
   FOCUS_BEFOCUS_OID    varchar(32) comment '��IDֵ��ָ��ȡ���ڹ�ע���ͣ������ע�ǻ�Ա����ָ�Ļ�Ա�ʺţ��������Ƭ����������Ƭ���������Լ��ƻ�����ָ�����Լ��ƻ�������',
   FOCUS_DATE           datetime,
   FOCUS_TYPE           tinyint comment '0:��Ա
            2:��Ƭ
            3:Լ��ƻ�',
   FOCUS_COUNT          int comment '����Ϊ��ֵ������',
   primary key (FOCUS_ID)
);

alter table YAW_MEMBER_FOCUS comment '��Щ�û�������ʲôʱ���ע�˻�Ա��ʲô����';

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
   MSG_STATUS           tinyint comment '0:����δ�ظ�
            1:�����ѻظ�
            2: ��������',
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
   ORDER_NO             varchar(32) not null comment '��Ʒ����-������ʱ�������(3λ����
            LZ-20140908120945109',
   ORDER_MID            varchar(32),
   ORDER_INCSERVICE_ID  int,
   ORDER_MNG_ID         varchar(32),
   ORDER_SUBMIT_TIME    datetime not null,
   ORDER_STATUS         tinyint not null comment '0��������
            1���Ѹ���
            2����ȡ��
            3���ѹ���
            4����ɾ��
            5:   ������',
   ORDER_TOTAL_MONEY    int not null,
   ORDER_INCSERVICE_NAME varchar(20) comment '0:    ��Լ���� 
            10:   �ο�����
            11����������
            20�����λ���
            30���οͻʹ�
            31�����λʹ�
            4:     �ö�
            5��  ��ҳͷ��
            6��  ������ϵ��ʽ
            7��  �鿴��ϵ��ʽ',
   ORDER_COUNT          int,
   ORDER_PAY_MODE       tinyint comment '1:֧��ƽ̨
            2:��̨���
            3:����ת��
            0:Լ���Ҹ�',
   ORDER_PAY_ORG        varchar(20),
   ORDER_PAY_TIME       datetime,
   ORDER_HANDLE_TIME    datetime,
   primary key (ORDER_NO)
);

alter table YAW_ORDER comment '�洢�û��µ��������ˮ';

/*==============================================================*/
/* Table: YAW_PHOTO                                             */
/*==============================================================*/
create table YAW_PHOTO
(
   PHOTO_ID             int not null auto_increment,
   PHOTO_TYPE           int comment '0:��ͨ��
            1:������
            2:������
            3:������
            4:���֤
            5:����֤
            6:����֤
            7:������
            ',
   PHOTO_MID            varchar(32),
   PHOTO_FOCUS_COUNT    int comment '�����������һ�Σ���Ϊ����עһ��',
   PHOTO_URL            varchar(255) comment 'ԭʼͼƬ�ĵ�ַURL;
            ����ͼ�ĵ�ַ����Ϊ��
            
            ԭʼͼƬ�ĵ�ַURL_����',
   PHOTO_TITLE          varchar(50),
   PHOTO_FORMAT         varchar(5),
   PHOTO_STATUS         tinyint comment '0��δ��֤
            1������֤',
   primary key (PHOTO_ID)
);

alter table YAW_PHOTO comment '����ԭ����Ƭ��Ϣ����������,���а��μ��ο͵�����������������һ��;';

/*==============================================================*/
/* Table: YAW_PHOTO_THUMBNAIL                                   */
/*==============================================================*/
create table YAW_PHOTO_THUMBNAIL
(
   THUMB_ID             int not null auto_increment,
   PHOTO_ID             int,
   THUMB_SIZE           tinyint comment '1:�б���
            2:ͷ����
            3:��ʲô�ٶ�',
   THUMB_URL            varchar(255),
   primary key (THUMB_ID)
);

alter table YAW_PHOTO_THUMBNAIL comment '�洢��Ƭ��������������ͼURL��ԭʼ����ͼƬURL�Ǵ����PHOTE��ESCORT��TOURIST����';

/*==============================================================*/
/* Table: YAW_POPULARIZE_RECORD                                 */
/*==============================================================*/
create table YAW_POPULARIZE_RECORD
(
   POPULARIZE_ID        int not null auto_increment,
   POPULARIZE_MID       varchar(32),
   POPULARIZE_EFFECTIVE_TIME datetime,
   POPULARIZE_TYPE      tinyint comment '1:������
            2:����',
   POPULARIZE_IS_REGISTED tinyint,
   POPULARIZE_URL       varchar(255),
   POPULARIZE_SHARETO   varchar(10) comment '0������
            1��΢��
            2��΢',
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
   RS_TYPE              tinyint comment '0��ƭ��ƭ��
            1�����Ǳ���
            2�����䲻��
            3�����ϲ�ʵ
            4���������',
   RS_CONTENT           varchar(500),
   RS_HANDLED           tinyint comment '0:δ����
            1:������/�Ѳ���
            2:����',
   primary key (RS_ID)
);

/*==============================================================*/
/* Table: YAW_R_INCSERVICE_MEMBER                               */
/*==============================================================*/
create table YAW_R_INCSERVICE_MEMBER
(
   RIM_ID               int not null,
   RIM_MID              varchar(32),
   RIM_INCSERVICE_ID    int comment '11�������ö�
            12�������ö�
            13�������ö�
             2�� ������ϵ��ʽ
             3����ҳͷ��',
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
   RTE_AUTO_MATCH       tinyint comment '0�����Զ�
            1���Զ�',
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
   TAG_CONTENT          varchar(30) comment '��������꣬ÿ��������ĸ��ֵĴʣ������","����',
   primary key (TAG_ID)
);

alter table YAW_TAG_RECORD comment '�������֮���ܣ�ֻ�ǲ�����������ô��';

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
   TOURIST_MOST_PRICE   int comment 'n��Ԫ/��
            -1������
            0:δ��',
   TOURIST_MESSAGE      varchar(500),
   TOURIST_LIKE_BODY    tinyint,
   TOURIST_LIKE_IMAGE   tinyint,
   TOURIST_LIKE_FEEL    tinyint,
   TOURIST_LIKE_WEIGHT  tinyint,
   TOURIST_LIKE_HEIGHT  int,
   TOURIST_TRYTO        varchar(100),
   TOURIST_LOVE         varchar(100),
   TOURIST_ORDER_WEIGHT int comment 'ֵԽ��Ȩ��Խ�ߣ�����Խ��ǰ
            ���ݶ���
             ��ֵ���񣬻�Ա���𣬳���ȡ������������������� ����������㷨���ۺ��趨��ֵ',
   primary key (TOURIST_MID)
);

/*==============================================================*/
/* Table: YAW_TRIPPLAN                                          */
/*==============================================================*/
create table YAW_TRIPPLAN
(
   TRIPPLAN_ID          int not null auto_increment,
   TRIPPLAN_MID         varchar(32),
   TRIPPLAN_STATUS      tinyint comment '0:������
            1:�����
            2:�ѹ���
            3;��ȡ��',
   TRIPPLAN_TITLE       varchar(50),
   TRIPPLAN_MONEY_MODEL tinyint comment '0������ȫ�����г��
            1������ȫ�����޳��
            2������AA�ƣ��������
            ',
   TRIPPLAN_TYPE        tinyint comment '0��������
            1��������
            2��������',
   TRIPPLAN_PUBLISH_TIME datetime,
   TRIPPLAN_DEPART_TIME datetime,
   TRIPPLAN_DEPART_ADDR varchar(20),
   TRIPPLAN_DESTINATION varchar(20),
   TRIPPLAN_DAY         tinyint comment 'סһ����һ�죬���������',
   TRIPPLAN_WANT_SEX    varchar(1),
   TRIPPLAN_WANT_AGE    tinyint,
   TRIPPLAN_WANT_PERSONS tinyint default 1,
   TRIPPLAN_OTHER       varchar(500),
   TRIPPLAN_ORDER_WEIGHT int comment 'ֵԽ��Ȩ��Խ�ߣ�����Խ��ǰ
            Ȩ�����ӣ���ע��(50%)+��Ա����Ȩ��(50%)',
   TRIPPLAN_BEFOCUS_COUNT int,
   primary key (TRIPPLAN_ID)
);

