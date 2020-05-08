## --- !Ups

CREATE TABLE account(
    id bigint primary key auto_increment,
    passport varchar(30) not null unique,
    password varchar(40) not null,
    createAt timestamp default current_timestamp,
    updateAt timestamp default current_timestamp,
    status tinyint default 0
);


## --- !Downs
DROP TABLE account;