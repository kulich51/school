package ru.hogwarts.school;

import netscape.javascript.JSObject;
import org.assertj.core.api.Assertions;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.hogwarts.school.controller.StudentController;
import ru.hogwarts.school.model.Student;

import java.util.Collection;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SchoolApplicationTests {

    @LocalServerPort
    private int port;

    @Autowired
    private StudentController studentController;

    @Autowired
    private TestRestTemplate restTemplate;

    private String resourceUrl;

    private static Student testStudent = new Student();

    private static final long TESTED_STUDENT_ID = 1L;

    private static final int DEFAULT_AGE = 30;

    @BeforeAll
    static void initStudent() {
        testStudent.setName("Vladimir Kulikov");
        testStudent.setAge(27);
    }


    @BeforeEach
    void init(){
        resourceUrl = "http://localhost:" + port + "/student/";
    }

    @Test
    void contextLoads() {
        assertThat(studentController).isNotNull();
    }

    @Test
    void testGetStudent() {
        ResponseEntity<Student> response = this.restTemplate.getForEntity(resourceUrl + TESTED_STUDENT_ID, Student.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void testGetStudentsByAge() {
        String filterByAge = "?age=27";
        Collection response = this.restTemplate.getForObject(
                resourceUrl + filterByAge,
                Collection.class
        );
        assertThat(response.size()).isGreaterThan(0);
    }

    @Test
    void testGetStudentsBetweenAge() {
        String filterBetweenAge = "?min=15&max=50";
        Collection response = this.restTemplate.getForObject(
                resourceUrl + filterBetweenAge,
                Collection.class
        );
        assertThat(response.size()).isGreaterThan(0);
    }

    @Test
    void testAddStudent() {
        HttpEntity<Student> request = new HttpEntity<>(testStudent);
        Student response = this.restTemplate.postForObject(resourceUrl, request, Student.class);
        testStudent.setId(response.getId());
        assertThat(response).isEqualTo(testStudent);
    }

    @Test
    void testUpdateStudent() {
        Student beforeUpdate = getDefaultStudent();
        System.out.println(beforeUpdate);

        beforeUpdate.setAge(DEFAULT_AGE);
        HttpEntity<Student> request = new HttpEntity<>(beforeUpdate);
        this.restTemplate.put(resourceUrl, request);

        Student response = getDefaultStudent();
        System.out.println(beforeUpdate);
        assertThat(response).isEqualTo(beforeUpdate);
    }

    private Student getDefaultStudent() {
        return this.restTemplate.getForObject(
                resourceUrl + TESTED_STUDENT_ID,
                Student.class
        );
    }





}
