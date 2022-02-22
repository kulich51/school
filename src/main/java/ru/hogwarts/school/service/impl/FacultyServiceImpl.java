package ru.hogwarts.school.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.service.FacultyService;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class FacultyServiceImpl implements FacultyService {

    private final FacultyRepository faculties;

    public FacultyServiceImpl(FacultyRepository faculties) {
        this.faculties = faculties;
    }

    @Override
    public Faculty getFaculty(Long id) {
        return faculties.findById(id).orElse(null);
    }

    @Override
    public Faculty addFaculty(Faculty faculty) {
        return faculties.save(faculty);
    }

    @Override
    public Faculty updateFaculty(Faculty faculty) {
        if (getFaculty(faculty.getId()) != null) {
            return faculties.save(faculty);
        }
        return null;
    }

    @Override
    public Faculty removeFaculty(Long id) {
        Faculty deleted = getFaculty(id);
        faculties.deleteById(id);
        return deleted;
    }

    @Override
    public Collection<Faculty> getFacultiesByColor(String color) {
        return faculties.findByColor(color);
    }
}
