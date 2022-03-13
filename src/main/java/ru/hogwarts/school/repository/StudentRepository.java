package ru.hogwarts.school.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.hogwarts.school.model.Student;

import java.util.Collection;

public interface StudentRepository extends JpaRepository<Student, Long> {

    final String GET_LAST_STUDENTS_QUERY = "SELECT * " +
            "FROM (SELECT * FROM student ORDER BY id DESC LIMIT :limit) AS sub " +
            "ORDER BY id";

    Collection<Student> findByAge(int age);
    Collection<Student> findByAgeBetween(int min, int max);

    @Query(value = "SELECT COUNT(*) FROM student", nativeQuery = true)
    Long countStudentById();

    @Query(value = "SELECT AVG(age) FROM student", nativeQuery = true)
    Integer getAverageAge();

    @Query(value = GET_LAST_STUDENTS_QUERY, nativeQuery = true)
    Collection<Student> getLastStudentsByLimit(@Param("limit") int limit);
}
