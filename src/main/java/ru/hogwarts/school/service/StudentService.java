package ru.hogwarts.school.service;

import org.springframework.http.ResponseEntity;
import ru.hogwarts.school.model.Student;

import java.util.Collection;

public interface StudentService {

    Student getStudent(Long id);
    Student addStudent(Student student);
    Student updateStudent(Student student);
    Student removeStudent(Long id);
    Collection<Student> getStudentsByAge(int age);
    Collection<Student> getStudentsBetweenAge(int min, int max);
}
