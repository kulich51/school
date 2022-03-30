package ru.hogwarts.school.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;
import ru.hogwarts.school.service.StudentService;

import java.util.Collection;
import java.util.Locale;
import java.util.stream.Collectors;

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

    @Override
    public Collection<String> getAllNamesStartedFrom(String starts_with) {
        return students.findAll()
                .parallelStream()
                .filter(student -> student.getName().startsWith(starts_with))
                .map(student -> student.getName().toUpperCase())
                .sorted()
                .collect(Collectors.toList());
    }

    @Override
    public Double getAverageAgeByStream() {
        return students.findAll()
                .parallelStream()
                .mapToInt(Student::getAge)
                .average()
                .getAsDouble();
    }

    @Override
    public void printStudents(boolean isSynchronized) {
        printStudent(1L, isSynchronized);
        printStudent(2L, isSynchronized);
        getStudentThread(new long[]{3L, 4L}, isSynchronized).start();
        getStudentThread(new long[]{5L, 6L}, isSynchronized).start();
    }

    private Thread getStudentThread(long[] ids, boolean isSynchronized) {
        return new Thread(
                () -> {
                    for (long id : ids) {
                        printStudent(id, isSynchronized);
                    }
                }
        );
    }

    private void printStudent(Long id, boolean isSynchronized) {
        if (isSynchronized == true) {
            synchronized (System.out) {
                System.out.println(students.getById(id));
            }
            return;
        }
        System.out.println(students.getById(id));
    }

}
