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

    private ResponseEntity<Student> getResponse(Student student) {
        if (student == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(student);
    }
}
