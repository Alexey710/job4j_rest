-- create database fullstack_auth;

create table person (
   id serial primary key not null,
   login varchar(2000),
   password varchar(2000),
   employee_id int references employees(id)
);

insert into person (login, password) values ('parsentev', '123');
insert into person (login, password, employee_id) values ('ban', '123', 2);
insert into person (login, password, employee_id) values ('ivan', '123', 1);

create table employees (
   id serial primary key not null,
   name varchar(2000) not null,
   surname varchar(2000) not null,
   tin varchar(2000) not null,
   created timestamp without time zone not null default now()
);

insert into employees (name, surname, tin)
values ('Ivan', 'Ivanov', '0001');

insert into employees (name, surname, tin)
values ('Ban', 'Gun', '0002');

