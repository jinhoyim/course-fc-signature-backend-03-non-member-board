create table board(
    id bigint not null auto_increment,
    board_name varchar(50) not null,
    status varchar(50) not null,
    primary key (id)
) engine innodb character set utf8mb4;

create table post(
    id bigint not null auto_increment,
    board_id bigint not null,
    username varchar(50) not null,
    password varchar(128) not null,
    email varchar(128),
    status varchar(50) not null,
    title varchar(100) not null,
    content text not null,
    posted_at datetime not null,
    primary key (id)
) engine innodb character set utf8mb4;

create table reply(
    id bigint not null auto_increment,
    post_id bigint not null,
    username varchar(50) not null,
    status varchar(50) not null,
    title varchar(100) not null,
    content text not null,
    replied_at datetime not null,
    primary key (id)
) engine innodb character set utf8mb4;

# Board-Post FK는 생략
alter table reply add foreign key(post_id) references post(id);