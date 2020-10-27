/* 用户表 */
create table if not exists `yz`.`users` (
`id` int not null primary key auto_increment comment 'id primary key',
`username` varchar(45) not null comment 'username',
`password` varchar(128) not null comment 'password',
`enabled` int not null default 1 comment 'enabled'
)engine=InnoDB default charset=utf8;

/* 权限表 */
create table if not exists `yz`.`authorities`(
`id` int not null auto_increment primary key,
`username` varchar(45) not null,
`authority` varchar(45) not null
)engine=InnoDB default charset=utf8;

insert ignore into `yz`.`authorities` values (null, 'admin', 'write');
insert ignore into `yz`.`users` values (null, 'admin', '$2a$10$gKMM8e0ixwOI5mlPfQ7RHeli5ioq.V/DkjqDu7zg1FEaK84HnvG2' ||
 '.', 1);