package ru.hogwarts.school.service.impl;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;
import ru.hogwarts.school.service.StudentService;

import java.util.Collection;

@Service
public class StudentServiceImpl implements StudentService {

    private final StudentRepository students;

    public StudentServiceImpl(StudentRepository students) {
        this.students = students;
    }

    @Override
    public Student getStudent(Long id) {
        return students.findById(id).orElse(null);
    }

    @Override
    public Student addStudent(Student student) {
        return students.save(student);
    }

    @Override
    public Student updateStudent(Student student) {
        if (getStudent(student.getId()) != null) {
            return students.save(student);
        }
        return null;
    }

    @Override
    public Collection<Student> getStudentsByAge(int age) {
        return students.findByAge(age);
    }

    @Override
    public Collection<Student> getStudentsBetweenAge(int min, int max) {
        return students.findByAgeBetween(min, max);
    }

    @Override
    public Long getStudentsCount() {
        return students.countStudentById();
    }

    @Override
    public Integer getAverageAge() {
        return students.getAverageAge();
    }

    @Override
    public Collection<Student> getLastStudents(int limit) {
        return students.getLastStudentsByLimit(limit);
    }

    @Override
    public Student removeStudent(Long id) {
        Student deleted = getStudent(id);
        students.deleteById(id);
        return deleted;
    }
}
