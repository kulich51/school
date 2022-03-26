package ru.hogwarts.school.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private Logger logger = LoggerFactory.getLogger(StudentServiceImpl.class);

    @Override
    public Student getStudent(Long id) {
        logger.info("getStudent called");
        return students.findById(id).orElse(null);
    }

    @Override
    public Student addStudent(Student student) {
        logger.info("addStudent called");
        return students.save(student);
    }

    @Override
    public Student updateStudent(Student student) {
        logger.info("updateStudent called");
        if (getStudent(student.getId()) != null) {
            return students.save(student);
        }
        return null;
    }

    @Override
    public Collection<Student> getStudentsByAge(int age) {
        logger.info("getStudentsByAge called");
        return students.findByAge(age);
    }

    @Override
    public Collection<Student> getStudentsBetweenAge(int min, int max) {
        logger.info("getStudentsBetweenAge called");
        return students.findByAgeBetween(min, max);
    }

    @Override
    public Long getStudentsCount() {
        logger.info("getStudentsCount called");
        return students.countStudentById();
    }

    @Override
    public Integer getAverageAge() {
        logger.info("getAverageAge called");
        return students.getAverageAge();
    }

    @Override
    public Collection<Student> getLastStudents(int limit) {
        logger.info("getLastStudents called");
        return students.getLastStudentsByLimit(limit);
    }

    @Override
    public Student removeStudent(Long id) {
        Student deleted = getStudent(id);
        students.deleteById(id);
        return deleted;
    }
}
