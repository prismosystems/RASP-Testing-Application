CREATE DATABASE sqlinject;

\c sqlinject;

CREATE TABLE public.customers (
	"first" varchar(50) NULL,
	"last" varchar(50) NULL,
	"password" varchar(50) NULL
);

CREATE OR REPLACE PROCEDURE public.verifyuser(username character varying, password character varying, validuser integer)
 LANGUAGE sql
AS $procedure$
select * from customers where last = USERNAME and password = password;
$procedure$
;


CREATE OR REPLACE PROCEDURE public.sql_array(a character[], b character varying)
 LANGUAGE sql
AS $procedure$
select * from customers where last = any (a) and password = b;
$procedure$
;