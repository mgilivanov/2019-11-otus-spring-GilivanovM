drop table if exists books;
drop table if exists authors;
drop table if exists genres;
drop table if exists book_authors;
drop table if exists book_genres;
create table books (id bigint generated by default as identity primary key, name varchar(255));
create table authors (id bigint generated by default as identity primary key, name varchar(255));
create table genres (id bigint generated by default as identity primary key, name varchar(255));
create table book_authors (book_id bigint, author_id bigint, foreign key (book_id) references books (id), foreign key (author_id) references authors (id));
create table book_genres (book_id bigint, genre_id bigint, foreign key (book_id) references books (id), foreign key (genre_id) references genres (id));
create table comments (id bigint generated by default as identity primary key, book_id bigint, text varchar(255));