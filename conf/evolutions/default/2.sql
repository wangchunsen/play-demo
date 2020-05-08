## --- !Ups
insert into account (passport, password, status) values('demo', '123456', 0);
insert into account (passport, password, status) values('disable_demo', '123456', 1);

## --- !Downs:w
delete from table where 1=1;