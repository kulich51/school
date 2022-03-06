package ru.hogwarts.school;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.hogwarts.school.controller.FacultyController;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.service.impl.FacultyServiceImpl;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(FacultyController.class)
class SchoolApplicationMockTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FacultyRepository facultyRepository;

    @SpyBean
    private FacultyServiceImpl facultyService;

    private final String RESOURCE_URL = "http://localhost:8080/faculty/";

    private final Faculty TESTED_FACULTY = new Faculty();
    private final JSONObject TESTED_FACULTY_OBJECT = new JSONObject();

    @BeforeEach
    void init() throws JSONException {
        TESTED_FACULTY.setId(777L);
        TESTED_FACULTY.setName("Slitherin");
        TESTED_FACULTY.setColor("Green");

        TESTED_FACULTY_OBJECT.put("id", TESTED_FACULTY.getId());
        TESTED_FACULTY_OBJECT.put("name", TESTED_FACULTY.getName());
        TESTED_FACULTY_OBJECT.put("color", TESTED_FACULTY.getColor());
    }

    @Test
    void testGetFaculty() throws Exception {
        when(facultyRepository.findById(any(Long.class))).thenReturn(Optional.of(TESTED_FACULTY));

        this.mockMvc.perform(MockMvcRequestBuilders.get(RESOURCE_URL + TESTED_FACULTY.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.name").value("Slitherin"))
                .andExpect(jsonPath("$.color").value("Green"));
    }

    @Test
    void testAddFaculty() throws Exception {
        when(facultyRepository.save(any(Faculty.class))).thenReturn(TESTED_FACULTY);

        this.mockMvc.perform(MockMvcRequestBuilders.post(RESOURCE_URL)
                        .content(TESTED_FACULTY_OBJECT.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(TESTED_FACULTY.getId()))
                .andExpect(jsonPath("$.name").value(TESTED_FACULTY.getName()))
                .andExpect(jsonPath("$.color").value(TESTED_FACULTY.getColor()));
    }

    @Test
    void testUpdateFaculty() throws Exception {
        String newName = "MEPhI kaf2";
        TESTED_FACULTY_OBJECT.put("name", newName);
        Faculty updated = TESTED_FACULTY;
        updated.setName(newName);

        when(facultyRepository.findById(any(Long.class))).thenReturn(Optional.of(TESTED_FACULTY));
        when(facultyRepository.save(any(Faculty.class))).thenReturn(updated);

        this.mockMvc.perform(MockMvcRequestBuilders.put(RESOURCE_URL)
                        .content(TESTED_FACULTY_OBJECT.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(TESTED_FACULTY.getId()))
                .andExpect(jsonPath("$.name").value(newName))
                .andExpect(jsonPath("$.color").value(TESTED_FACULTY.getColor()));
    }

    @Test
    void removeFaculty() throws Exception {
        when(facultyRepository.findById(any(Long.class))).thenReturn(Optional.of(TESTED_FACULTY));

        this.mockMvc.perform(MockMvcRequestBuilders.delete(RESOURCE_URL + TESTED_FACULTY.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(TESTED_FACULTY.getId()))
                .andExpect(jsonPath("$.name").value(TESTED_FACULTY.getName()))
                .andExpect(jsonPath("$.color").value(TESTED_FACULTY.getColor()));
    }

    @Test
    void testGetFacultiesByColor() throws Exception {
        when(facultyRepository.findByColor(any(String.class))).thenReturn(List.of(TESTED_FACULTY));

        this.mockMvc.perform(MockMvcRequestBuilders.get(RESOURCE_URL).param("color", TESTED_FACULTY.getColor()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(TESTED_FACULTY.getId()))
                .andExpect(jsonPath("$[0].name").value(TESTED_FACULTY.getName()))
                .andExpect(jsonPath("$[0].color").value(TESTED_FACULTY.getColor()));
    }

    @Test
    void testGetFacultiesByNameAndColor() throws Exception {
        when(facultyRepository.findByNameOrColorIgnoreCase(any(String.class), any(String.class)))
                .thenReturn(List.of(TESTED_FACULTY));

        this.mockMvc.perform(MockMvcRequestBuilders
                        .get(RESOURCE_URL)
                        .param("name", TESTED_FACULTY.getName())
                        .param("color", TESTED_FACULTY.getColor()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(TESTED_FACULTY.getId()))
                .andExpect(jsonPath("$[0].name").value(TESTED_FACULTY.getName()))
                .andExpect(jsonPath("$[0].color").value(TESTED_FACULTY.getColor()));
    }
}
