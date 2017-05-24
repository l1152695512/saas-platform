insert into `Tenant` (`id`,`name`,`description`,`createTime`,`status`) values ('tonglukuaijian','通路快建','上海市通路快建营销咨询有限公司',now(),'AVAILABLE');
insert into `Tenant` (`id`,`name`,`description`,`createTime`,`status`) values ('malls','猫市','猫市五洲精选',now(),'AVAILABLE');

insert into `Application` (`id`,`name`,`group`,`description`,`version`,`createTime`,`status`) values
  ('survey','survey','example','malls公司提供的问卷服务','0.0.1-SNAPSHOT',now(),'AVAILABLE');
insert into `Application` (`id`,`name`,`group`,`description`,`version`,`createTime`,`status`) values
  ('task','task','example','malls公司提供的众包任务平台服务','0.0.1-SNAPSHOT',now(),'AVAILABLE');
insert into `Application` (`id`,`name`,`group`,`description`,`version`,`createTime`,`status`) values
  ('account','account','example','malls公司提供的账务管理服务','0.0.1-SNAPSHOT',now(),'AVAILABLE');