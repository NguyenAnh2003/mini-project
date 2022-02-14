create database managementProject

use managementProject


create table employees (
ID varchar(10),
Name varchar(100),
Address varchar(100),
Salary money,
Job varchar(100),
Gender varchar(100),
Birthday date,
)
drop table employees
insert into employees values('em1', 'aa', 'America', 100, 'AI engineer', 'male', '2003-2-3');
select * from employees

delete from employees where id = 'em7';

create table managers (
ID varchar(100) primary key,
Name varchar(100),
Address varchar(100),
Salary money,
Job varchar(100),
Gender varchar(100),
Birthday date,
)


drop table managers
insert into managers values('ma1', 'aa', 'America', 100, 'AI engineer', 'male', '2003-2-3');


create table userAccess(
id int primary key,
username varchar(100),
password varchar(100)
)

insert into userAccess values(1, 'nguyenanh', 'cunho123');
insert into userAccess values(2, 'nguyenanh123', 'cunho123');
insert into userAccess values(3, 'nguyenanh2003', 'cunho123');