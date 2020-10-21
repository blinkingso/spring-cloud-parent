create table if not exists `yz`.`tb_client_details` (
`id` bigint(11) primary key auto_increment,
`client_id` varchar(50) not null unique,
scope varchar(200) not null,
authorized_grant_types varchar(200) not null,
web_server_redirect_uri text,
authorities text,
access_token_validity int,
refresh_token_validity int,
additional_information text,
autoapprove text
) engine=innodb default charset=utf8;