--liquibase formatted sql

--changeset kulik:1

create index name_student_index on student(name);

--changeset kulik:2

create index name_color_faculty_index on faculty(name, color);
