DROP TABLE IF EXISTS `Job`;
CREATE TABLE `Job` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `jobName` varchar(100) NOT NULL DEFAULT '' COMMENT '��������',
  `cron` varchar(100) NOT NULL DEFAULT '' COMMENT 'cron',
  `timeout` int(11) NOT NULL DEFAULT '0' COMMENT '��ʱʱ�䣨ms�룩',
  `type` tinyint(4) NOT NULL DEFAULT '1' COMMENT 'type(1.http 2.class)',
  `category` varchar(100) NOT NULL DEFAULT '' COMMENT '�������',
  `url` varchar(500) NOT NULL DEFAULT '' COMMENT 'http�������ַ',
  `handleHost` varchar(100) NOT NULL DEFAULT '' COMMENT 'ִ��job����',
  `lastExeTime` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '���ִ��ʱ��',
  `nextExeTime` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '�´�ִ��ʱ��',
  `status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '(0���á�1��ֹ)',
  `updateTime` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' ON UPDATE CURRENT_TIMESTAMP COMMENT '�޸�ʱ��',
  `createTime` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '����ʱ��',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `JobHis`;
CREATE TABLE `JobHis` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `jobId` bigint(20) NOT NULL,
  `jobName` varchar(100) NOT NULL DEFAULT '' COMMENT '��������',
  `cron` varchar(100) NOT NULL DEFAULT '' COMMENT 'cron',
  `timeout` int(11) NOT NULL DEFAULT '0' COMMENT '��ʱʱ�䣨s�룩',
  `type` tinyint(4) NOT NULL DEFAULT '1' COMMENT 'type(1.http 2.class)',
  `url` varchar(500) NOT NULL DEFAULT '' COMMENT 'http�������ַ',
  `lastExeTime` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '���ִ��ʱ��',
  `handleHost` varchar(100) NOT NULL,
  `executeTimes` bigint(20) NOT NULL DEFAULT '0' COMMENT 'ִ��ʱ�� ��ms�룩',
  `errorMsg` varchar(4000) NOT NULL,
  `createTime` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '����ʱ��',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;