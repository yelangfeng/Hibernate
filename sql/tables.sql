drop table cst_linkman
drop table cst_customer
drop table account

CREATE TABLE  cst_customer  (
   cust_id  int identity(1,1) primary key,
   cust_name  varchar(32) NOT NULL ,
   cust_source  varchar(32) DEFAULT NULL ,
   cust_industry  varchar(32) DEFAULT NULL,
   cust_level  varchar(32) DEFAULT NULL,
   cust_phone  varchar(64) DEFAULT NULL,
   cust_mobile  varchar(16) DEFAULT NULL
)

CREATE TABLE  cst_linkman  (
   lkm_id  int identity(1,1) primary key,
   lkm_name  varchar(16) DEFAULT NULL,
   lkm_cust_id  int DEFAULT NULL,
   lkm_gender  char(1) DEFAULT NULL,
   lkm_phone  varchar(16) DEFAULT NULL,
   lkm_mobile  varchar(16) DEFAULT NULL,
   lkm_email  varchar(64) DEFAULT NULL,
   lkm_qq  varchar(16) DEFAULT NULL,
   lkm_position  varchar(16) DEFAULT NULL,
   lkm_memo  varchar(512) DEFAULT NULL,
   foreign key(lkm_cust_id) references cst_customer(cust_id)
)

create table account(
   id int identity(1,1) primary key,
   name varchar(10),
   money money
   )
