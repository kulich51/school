select student.name, student.age, f.name
from student left join faculty f on f.id = student.faculty_id;

select *
from student inner join avatar a on student.id = a.student_id;