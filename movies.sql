create database movies;

use movies;

create table credentials (id int not null auto_increment, username varchar(6) not null, 
password varchar(6) not null, affiliation int, primary key (id));

create table movielist (id int not null auto_increment, moviename varchar(35) not null, 
director varchar(20) not null, year int, genre varchar(10), primary key (id));
