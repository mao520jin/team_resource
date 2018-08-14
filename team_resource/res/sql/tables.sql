权限相关设计数据表
实际使用时，表名需要改，字段以实际业务需要进行修改
CREATE TABLE `resource` (
  `RESOURCE_ID` varchar(32) NOT NULL,
  `PARENT_ID` varchar(32) DEFAULT NULL COMMENT '父级资源ID',
  `RES_TYPE` tinyint(2) DEFAULT NULL COMMENT '菜单级别（1,2,3,4级）',
  `SORT` tinyint(2) DEFAULT NULL COMMENT '同级别菜单排序',
  `RES_KEY` varchar(255) NOT NULL COMMENT '资源标识',
  `RES_NAME` varchar(128) NOT NULL COMMENT '资源名称',
  `ICON` varchar(1000) DEFAULT NULL COMMENT '图标',
  `URL` varchar(128) DEFAULT NULL COMMENT '菜单url',
  `STATUS` tinyint(2) DEFAULT NULL COMMENT '是否可用（1可用，0禁用）',
  `CREATE_TIME` varchar(32) DEFAULT NULL COMMENT '创建时间',
  `CREATE_BY` varchar(32) DEFAULT NULL COMMENT '创建人',
  `UPDATE_TIME` varchar(32) DEFAULT NULL COMMENT '更新时间',
  `UPDATE_BY` varchar(32) DEFAULT NULL COMMENT '更新人',
  PRIMARY KEY (`RESOURCE_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='资源管理表'

CREATE TABLE `role` (
  `ROLE_ID` varchar(32) NOT NULL,
  `ROLE_NAME` varchar(30) NOT NULL,
  `SCORE` smallint(2) DEFAULT '10' COMMENT '权重（0-10分）',
  `STATUS` tinyint(2) NOT NULL COMMENT '是否可用（1可用，0禁用）',
  `CREATE_TIME` varchar(32) DEFAULT NULL,
  `CREATE_BY` varchar(32) DEFAULT NULL COMMENT '创建人',
  PRIMARY KEY (`ROLE_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色管理表'

CREATE TABLE `role_resource` (
  `ROLE_RESOURCE_ID` varchar(32) NOT NULL,
  `ROLE_ID` varchar(32) NOT NULL,
  `RESOURCE_ID` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`ROLE_RESOURCE_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色资源表'

CREATE TABLE `sys_user` (
  `SYS_USER_ID` varchar(32) NOT NULL,
  `USERNAME` varchar(20) NOT NULL COMMENT '用户名',
  `PASSWORD` varchar(128) NOT NULL COMMENT '密码',
  `TRUE_NAME` varchar(32) DEFAULT NULL COMMENT '真实名称',
  `MOBILE` varchar(32) DEFAULT NULL COMMENT '手机号码',
  `STATUS` tinyint(2) NOT NULL COMMENT '是否可用（1可用，0禁用）',
  `IS_SHOW` tinyint(2) DEFAULT NULL COMMENT '1=启用，0=停用',
  `CREATE_TIME` varchar(32) DEFAULT NULL COMMENT '创建人',
  `CREATE_BY` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`SYS_USER_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户管理表'

CREATE TABLE `user_role` (
  `USER_ROLE_ID` varchar(32) NOT NULL,
  `SYS_USER_ID` varchar(32) NOT NULL,
  `ROLE_ID` varchar(32) NOT NULL,
  PRIMARY KEY (`USER_ROLE_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户角色表'

初始化admin用户（资源信息需要提前加好）

INSERT INTO `sys_user` ( `SYS_USER_ID`, `USERNAME`, `PASSWORD`, `TRUE_NAME`, `MOBILE`, `STATUS`, `IS_SHOW`, `CREATE_TIME`, `CREATE_BY` ) 
VALUES ( 'admin', 'admin', 'vst123456', '超管', '120', '1', '1', NOW(), 'admin' );

INSERT INTO `role` ( `ROLE_ID`, `ROLE_NAME`, `SCORE`, `STATUS`, `CREATE_TIME`, `CREATE_BY` ) VALUES 
( 'admin', '超管', '11', '1', NOW(), 'admin' );

INSERT INTO `user_role` ( `USER_ROLE_ID`, `SYS_USER_ID`, `ROLE_ID` ) 
VALUES ( 'admin', 'admin', 'admin'  );

DELETE FROM `role_resource` WHERE `role_id` = 'admin';

INSERT INTO `role_resource` SELECT SUBSTRING(RAND(),3,20 ) , 'admin', `RESOURCE_ID` FROM `resource` WHERE `RES_TYPE` = 4 ;







