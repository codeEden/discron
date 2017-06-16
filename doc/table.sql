DROP TABLE IF EXISTS `Job`;
CREATE TABLE `Job` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `jobName` varchar(100) NOT NULL DEFAULT '' COMMENT '任务名称',
  `cron` varchar(100) NOT NULL DEFAULT '' COMMENT 'cron',
  `timeout` int(11) NOT NULL DEFAULT '0' COMMENT '超时时间（ms秒）',
  `type` tinyint(4) NOT NULL DEFAULT '1' COMMENT 'type(1.http 2.class)',
  `category` varchar(100) NOT NULL DEFAULT '' COMMENT '任务类别',
  `url` varchar(500) NOT NULL DEFAULT '' COMMENT 'http或者类地址',
  `handleHost` varchar(100) NOT NULL DEFAULT '' COMMENT '执行job机器',
  `lastExeTime` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '最后执行时间',
  `nextExeTime` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '下次执行时间',
  `status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '(0启用、1禁止)',
  `updateTime` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `createTime` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `JobHis`;
CREATE TABLE `JobHis` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `jobId` bigint(20) NOT NULL,
  `jobName` varchar(100) NOT NULL DEFAULT '' COMMENT '任务名称',
  `cron` varchar(100) NOT NULL DEFAULT '' COMMENT 'cron',
  `timeout` int(11) NOT NULL DEFAULT '0' COMMENT '超时时间（s秒）',
  `type` tinyint(4) NOT NULL DEFAULT '1' COMMENT 'type(1.http 2.class)',
  `url` varchar(500) NOT NULL DEFAULT '' COMMENT 'http或者类地址',
  `lastExeTime` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '最后执行时间',
  `handleHost` varchar(100) NOT NULL,
  `executeTimes` bigint(20) NOT NULL DEFAULT '0' COMMENT '执行时常 （ms秒）',
  `errorMsg` varchar(4000) NOT NULL,
  `createTime` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;