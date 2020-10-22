-- auto-generated definition
create table tb_client_details
(
    id                      bigint auto_increment primary key,
    client_id               varchar(50)  not null,
    client_secret           text not null,
    scope                   varchar(200) not null,
    resource_ids          text null ,
    authorized_grant_types  varchar(200) not null,
    web_server_redirect_uri text         null,
    authorities             text         null,
    access_token_validity   int          null,
    refresh_token_validity  int          null,
    additional_information  text         null,
    auto_approve            text         null,
    constraint client_id
        unique (client_id)
) charset = utf8;
