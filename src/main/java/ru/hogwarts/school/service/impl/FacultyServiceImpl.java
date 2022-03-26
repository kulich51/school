package ru.hogwarts.school.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.service.FacultyService;

import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class FacultyServiceImpl implements FacultyService {

    private final FacultyRepository faculties;

    public FacultyServiceImpl(FacultyRepository faculties) {
        this.faculties = faculties;
    }

    private Logger logger = LoggerFactory.getLogger(StudentServiceImpl.class);

    @Override
    public Faculty getFaculty(Long id) {
        logger.info("getFaculty called");
        return faculties.findById(id).orElse(null);
    }

    @Override
    public Faculty addFaculty(Faculty faculty) {
        logger.info("addFaculty called");
        return faculties.save(faculty);
    }

    @Override
    public Faculty updateFaculty(Faculty faculty) {
        logger.info("updateFaculty called");
        if (getFaculty(faculty.getId()) != null) {
            return faculties.save(faculty);
        }
        return null;
    }

    @Override
    public Faculty removeFaculty(Long id) {
        logger.info("removeFaculty called");
        Faculty deleted = getFaculty(id);
        faculties.deleteById(id);
        return deleted;
    }

    @Override
    public Collection<Faculty> getFacultiesByColor(String color) {
        logger.info("getFacultiesByColor called");
        return faculties.findByColor(color);
    }

    @Override
    public Collection<Faculty> getFacultiesByNameAndColor(String name, String color) {
        logger.info("getFacultiesByNameAndColor called");
        return faculties.findByNameOrColorIgnoreCase(name, color);
    }

    @Override
    public String getMaxFacultyName() {
        return faculties.findAll()
                .parallelStream()
                .map(faculty -> faculty.getName())
                .max(Comparator.comparing(String::length)).orElseThrow();
    }
}
