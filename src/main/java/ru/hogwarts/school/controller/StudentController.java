package ru.hogwarts.school.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.StudentService;

import java.util.Collection;

@RestController
@RequestMapping("student")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("{id}")
    public ResponseEntity<Student> getStudent(@PathVariable Long id) {
        Student student = studentService.getStudent(id);
        return getResponse(student);
    }

    @PostMapping
    public ResponseEntity<Student> addStudent(@RequestBody Student student) {
        Student newStudent = studentService.addStudent(student);
        return ResponseEntity.ok(newStudent);
    }

    @PutMapping
    public ResponseEntity<Student> updateStudent(@RequestBody Student student) {
        Student updated = studentService.updateStudent(student);
        return getResponse(updated);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Student> removeStudent(@PathVariable Long id) {
        Student removed = studentService.removeStudent(id);
        return getResponse(removed);
    }

    @GetMapping(params = "age")
    public ResponseEntity<Collection<Student>> getStudentsByAge(@RequestParam int age) {
        return ResponseEntity.ok(studentService.getStudentsByAge(age));
    }

    @GetMapping(params = {"min", "max"})
    public ResponseEntity<Collection<Student>> getStudentsBetweenAge(@RequestParam int min,
                                                                     @RequestParam int max) {
        return ResponseEntity.ok(studentService.getStudentsBetweenAge(min, max));
    }

    @GetMapping("count")
    public ResponseEntity<Long> getStudentsCount() {
        return ResponseEntity.ok(studentService.getStudentsCount());
    }

    @GetMapping("average-age")
    public ResponseEntity<Integer> getAverageAge() {
        return ResponseEntity.ok(studentService.getAverageAge());
    }

    @GetMapping("last_students")
    public ResponseEntity<Collection<Student>> getLastStudents(@RequestParam(required = false, defaultValue = "5") int limit) {
        return ResponseEntity.ok(studentService.getLastStudents(limit));
    }

    @GetMapping(params = "starts_with")
    public ResponseEntity<Collection<String>> getAllNamesStartedFrom(@RequestParam String starts_with) {
        return ResponseEntity.ok(studentService.getAllNamesStartedFrom(starts_with));
    }

    @GetMapping("average-age/by-stream")
    public ResponseEntity<Double> getAverageAgeByStream() {
        return ResponseEntity.ok(studentService.getAverageAgeByStream());
    }

    @GetMapping("print-by-threads")
    public void printStudents() {
        studentService.printStudents(false);
    }

    @GetMapping("print-by-threads/sync")
    public void printStudentsSync() {
        studentService.printStudents(true);
    }

    private ResponseEntity<Student> getResponse(Student student) {
        if (student == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(student);
    }
}
