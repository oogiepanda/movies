create database movies;

CREATE TABLE credentials
(
   id int PRIMARY KEY NOT NULL,
   username varchar(10) NOT NULL,
   password varchar(10) NOT NULL,
   affiliation int
)
;
CREATE UNIQUE INDEX PRIMARY ON credentials(id)
;

CREATE TABLE movielist
(
   id int PRIMARY KEY NOT NULL,
   moviename varchar(35) NOT NULL,
   director varchar(20),
   year int,
   genre varchar(10)
)
;
CREATE UNIQUE INDEX PRIMARY ON movielist(id)
;
