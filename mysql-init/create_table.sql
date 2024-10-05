create database if not exists oj;

use oj;

create table question
(
    id                    bigint auto_increment comment 'id'
        primary key,
    title                 varchar(512) charset utf8mb4       null comment '标题',
    content               text charset utf8mb4               null comment '内容',
    tags                  varchar(1024) charset utf8mb4      null comment '标签列表（json数组）',
    answer                text charset utf8mb4               null comment '题目答案',
    submitNum             int      default 0                 not null comment '题目提交数',
    acceptedNum           int      default 0                 not null comment '题目通过数',
    judgeCase             text charset utf8mb4               null comment '判题用例（JSON数组）',
    needVip               tinyint                            null comment '是否仅会员可见',
    judgeConfig           text charset utf8mb4               null comment '判题配置（json)',
    thumbNum              int      default 0                 not null comment '点赞数',
    favourNum             int      default 0                 not null comment '收藏数',
    userId                bigint                             not null comment '创建用户id',
    createTime            datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    updateTime            datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    isDelete              tinyint  default 0                 not null comment '是否删除',
    mainClassTemplate     text                               null comment '主类模板',
    solutionClassTemplate text                               null comment '答案类模板'
)
    comment '题目' collate = utf8mb4_unicode_ci;

create index idx_userId
    on question (userId);

create table question_bank
(
    id            bigint auto_increment comment 'id'
        primary key,
    title         varchar(256)                       null comment '标题',
    description   text                               null comment '描述',
    picture       varchar(2048)                      null comment '图片',
    userId        bigint                             not null comment '创建用户 id',
    editTime      datetime default CURRENT_TIMESTAMP not null comment '编辑时间',
    createTime    datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    updateTime    datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    isDelete      tinyint  default 0                 not null comment '是否删除',
    reviewStatus  int      default 0                 not null comment '状态：0-待审核, 1-通过, 2-拒绝',
    reviewMessage varchar(512)                       null comment '审核信息',
    reviewerId    bigint                             null comment '审核人 id',
    reviewTime    datetime                           null comment '审核时间',
    priority      int      default 0                 not null comment '优先级',
    viewNum       int      default 0                 not null comment '浏览量'
)
    comment '题库' collate = utf8mb4_unicode_ci;

create index idx_title
    on question_bank (title);

create table question_bank_question
(
    id             bigint auto_increment comment 'id'
        primary key,
    questionBankId bigint                             not null comment '题库 id',
    questionId     bigint                             not null comment '题目 id',
    userId         bigint                             not null comment '创建用户 id',
    createTime     datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    updateTime     datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    constraint questionBankId
        unique (questionBankId, questionId)
)
    comment '题库题目' collate = utf8mb4_unicode_ci;

create table question_submit
(
    id         bigint auto_increment comment 'id'
        primary key,
    language   varchar(128)                       not null comment '编程语言',
    code       text                               not null comment '用户代码',
    judgeInfo  text                               null comment '判题信息（json）',
    status     int      default 0                 not null comment '判题状态（0-待判题；1-判题中；2-成功；3-失败）',
    questionId bigint                             not null comment '题目id',
    userId     bigint                             not null comment '创建用户id',
    createTime datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    updateTime datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    isDelete   tinyint  default 0                 not null comment '是否删除'
)
    comment '题目提交表';

create index idx_questionId
    on question_submit (questionId);

create index idx_userId
    on question_submit (userId);

create table user
(
    id            bigint auto_increment comment 'id'
        primary key,
    userAccount   varchar(256)                           not null comment '账号',
    userPassword  varchar(512)                           not null comment '密码',
    unionId       varchar(256)                           null comment '微信开放平台id',
    mpOpenId      varchar(256)                           null comment '公众号openId',
    userName      varchar(256)                           null comment '用户昵称',
    userAvatar    varchar(1024)                          null comment '用户头像',
    userProfile   varchar(512)                           null comment '用户简介',
    userRole      varchar(256) default 'user'            not null comment '用户角色：user/admin/ban',
    editTime      datetime     default CURRENT_TIMESTAMP not null comment '编辑时间',
    createTime    datetime     default CURRENT_TIMESTAMP not null comment '创建时间',
    updateTime    datetime     default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    isDelete      tinyint      default 0                 not null comment '是否删除',
    vipExpireTime datetime                               null comment '会员过期时间',
    vipCode       varchar(128)                           null comment '会员兑换码',
    vipNumber     bigint                                 null comment '会员编号',
    shareCode     varchar(20)                            null comment '分享码',
    inviteUser    bigint                                 null comment '邀请用户 id'
)
    comment '用户' collate = utf8mb4_unicode_ci;

create index idx_unionId
    on user (unionId);

insert into user (userAccount, userPassword, userName, userRole) values ('admin', '9d2c23bfdddb73196d3597d19a4ce844', 'admin', 'admin');