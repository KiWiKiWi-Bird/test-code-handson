drop table member;
create table if not exists member (
    member_num int not null primary key auto_increment,
    member_name varchar(30)  null,
    email varchar(100) null
);
alter table member
    add constraint member_email_uk unique (email);
