-- Первый запрос
SELECT *
FROM student
WHERE age BETWEEN 10 and 20;

-- Второй запрос
SELECT name
FROM student;

-- Третий запрос
SELECT *
FROM student
WHERE name ILIKE '%О%';

-- Четвертый запрос
SELECT *
FROM student
WHERE age < id;

-- Пятый запрос
SELECT *
FROM student
ORDER BY age;

