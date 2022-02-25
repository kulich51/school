package ru.hogwarts.school.service;

import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;

import java.util.Collection;

public interface FacultyService {

    Faculty getFaculty(Long id);
    Faculty addFaculty(Faculty faculty);
    Faculty updateFaculty(Faculty faculty);
    Faculty removeFaculty(Long id);
    Collection<Faculty> getFacultiesByColor(String color);
    Collection<Faculty> getFacultiesByNameAndColor(String name, String color);
}
