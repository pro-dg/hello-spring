SELECT * FROM MEMBER

create table member
(
    id bigint generated by default as identity,
    name varchar(255),
    primary key(id)
);

insert into member (name) values ('m1');