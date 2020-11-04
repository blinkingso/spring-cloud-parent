create schema `spring`;
/*users*/
create table if not exists `spring`.`user`(
`id` int(11) primary key not null auto_increment,
`username` varchar(45) unique not null,
`password` text not null
)engine=InnoDB default charset=utf8;

/*opt configuration*/
create table if not exists `spring`.`otp`(
`id` int(11) primary key not null auto_increment,
`username` varchar(11) not null,
`code` varchar(45) not null
)engine=InnoDB default charset=utf8;