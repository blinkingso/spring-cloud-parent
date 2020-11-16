drop table spring.tb_client_details;
create table if not exists `spring`.`tb_client_details`
(
    `id`                        int(11) primary key not null auto_increment,
    `client_id`                 varchar(45) unique  not null,
    `client_secret`             text                not null,
    `client_name`               varchar(45)         not null,
    `redirect_urls`             text                not null,
    `resource_ids`              text                not null,
    `authorization_grant_types` mediumtext          not null,
    `scopes`                    mediumtext          not null,
    `authorities`               mediumtext          not null,
    `create_time`               timestamp default current_timestamp
) engine = InnoDB
  default charset = utf8;

insert ignore into `spring`.`tb_client_details`
values (null, 'db_client_authorization_code',
        '$2a$10$aM8eMHbfPyuoQ0.bWMfQ8ur5syLgq8jJ4iXG1L0POJBpXPuywkIA2',
        'db_client_using_authorization_code_grant_type',
        'http://localhost:8080',
        'rids',
        'authorization_code,refresh_token',
        'read,write',
        'ADMIN', NULL);
insert ignore into `spring`.`tb_client_details`
values (null, 'db_client_credentials',
        '$2a$10$aM8eMHbfPyuoQ0.bWMfQ8ur5syLgq8jJ4iXG1L0POJBpXPuywkIA2',
        'db_client_using_client_credentials_grant_type',
        'http://localhost:8080',
        'rids',
        'client_credentials,refresh_token',
        'read,write',
        'ADMIN', NULL);
insert ignore into `spring`.`tb_client_details`
values (null, 'db_client',
        '$2a$10$aM8eMHbfPyuoQ0.bWMfQ8ur5syLgq8jJ4iXG1L0POJBpXPuywkIA2',
        'db_client_all_grant_types',
        'http://localhost:8080',
        'rids',
        'password,authorization_code,client_credentials,refresh_token',
        'read,write',
        'ADMIN',
        NULL);

/*tables for token store*/
create table if not exists `spring`.`oauth_access_token`
(
    `token_id`          varchar(255) primary key not null,
    `token`             blob,
    `authentication_id` varchar(255) default null,
    `user_name`         varchar(255) default null,
    `client_id`         varchar(255) default null,
    `authentication`    blob,
    `refresh_token`     varchar(255) default null
) engine = InnoDB
  default charset = UTF8MB4;

create table if not exists `spring`.`oauth_refresh_token`
(
    `token_id`       varchar(255) not null,
    `token`          blob,
    `authentication` blob
) engine = InnoDB
  default charset = UTF8MB4;