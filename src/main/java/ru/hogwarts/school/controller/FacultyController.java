package ru.hogwarts.school.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.FacultyService;

import java.util.Collection;

@RestController
@RequestMapping("faculty")
public class FacultyController {

    private final FacultyService facultyService;

    public FacultyController(FacultyService facultyService) {
        this.facultyService = facultyService;
    }

    @GetMapping("{id}")
    public ResponseEntity<Faculty> getFaculty(@PathVariable Long id) {
        Faculty faculty = facultyService.getFaculty(id);
        return getResponse(faculty);
    }

    @PostMapping
    public ResponseEntity<Faculty> addFaculty(@RequestBody Faculty faculty) {
        Faculty newFaculty = facultyService.addFaculty(faculty);
        return ResponseEntity.ok(newFaculty);
    }

    @PutMapping
    public ResponseEntity<Faculty> updateFaculty(@RequestBody Faculty faculty) {
        Faculty updated = facultyService.updateFaculty(faculty);
        return getResponse(updated);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Faculty> removeFaculty(@PathVariable Long id) {
        Faculty removed = facultyService.removeFaculty(id);
        return getResponse(removed);
    }

    @GetMapping(params = "color")
    public ResponseEntity<Collection<Faculty>> getFacultiesByColor(@RequestParam String color) {
        return ResponseEntity.ok(facultyService.getFacultiesByColor(color));
    }

    @GetMapping(params = {"name", "color"})
    public ResponseEntity<Collection<Faculty>> getFacultiesByNameAndColor(@RequestParam String name,
                                                                          @RequestParam String color) {
        return ResponseEntity.ok(facultyService.getFacultiesByNameAndColor(name, color));
    }

    @GetMapping("max_length_name")
    public ResponseEntity<String> getMaxFacultyName() {
        return ResponseEntity.ok(facultyService.getMaxFacultyName());
    }

    private ResponseEntity<Faculty> getResponse(Faculty faculty) {
        if (faculty == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(faculty);
    }
}
