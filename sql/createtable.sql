
create table account(
id int identity(1,1) primary key,
name varchar(10),
money money 
)

create table emp(
empId int identity(1,1) primary key,
empName varchar(10),
empNo varchar(10),
dptId int
)

create table dpt(
dptId int identity(1,1) primary key,
dptName varchar(10),
dptNo varchar(10),
)