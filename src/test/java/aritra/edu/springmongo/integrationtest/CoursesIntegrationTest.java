package aritra.edu.springmongo.integrationtest;

import aritra.edu.springmongo.SpringMongoApp;
import aritra.edu.springmongo.document.Course;
import aritra.edu.springmongo.repo.CoursesRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = SpringMongoApp.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CoursesIntegrationTest {
    @Autowired
    CoursesRepository repository;

    @LocalServerPort
    int randomServerPort;

    @AfterEach
    void tearDown() {
        repository.deleteAll();
    }

    @Test
    public void saveCourses() {
        final String uri = "http://localhost:" + randomServerPort + "/courses/";
        Course newCourse = createCourse();

        TestRestTemplate restTemplate = new TestRestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        HttpEntity<Course> requestEntity = new HttpEntity<>(newCourse, headers);
        ResponseEntity<Course> result = restTemplate.postForEntity(uri, requestEntity,
                Course.class);
        assertEquals(201, result.getStatusCodeValue());
        Optional<Course> course = repository.findById("ENG-1011");
        assertTrue(course.isPresent());
    }

    @Test
    public void deleteCourseById() {
        TestRestTemplate restTemplate = new TestRestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        ResponseEntity<Course> result;

        //saving one document
        String uriForPost = "http://localhost:" + randomServerPort + "/courses/";
        Course newCourse = createCourse();
        HttpEntity<Course> requestEntity = new HttpEntity<>(newCourse, headers);
        result = restTemplate.postForEntity(uriForPost, requestEntity, Course.class);
        assertEquals(201, result.getStatusCodeValue());
        Optional<Course> course = repository.findById("ENG-1011");
        assertTrue(course.isPresent());

        //deleting document
        String uri = "http://localhost:" + randomServerPort + "/courses/" + "ENG-1011";
        result = restTemplate.exchange(uri, HttpMethod.DELETE, null, Course.class);
        assertEquals(200, result.getStatusCodeValue());
        Optional<Course> courseAfterDelete = repository.findById("ENG-1011");
        assertTrue(!courseAfterDelete.isPresent());
    }

    private Course createCourse() {
        String[] students = {"Paul", "Parker"};
        String[] ratings = {"5.0", "4.1", "3.5"};
        Course course = new Course();
        course.setId("ENG-1011");
        course.setName("Comparative English");
        course.setDescription("Always Make a big deal");
        course.setStudents(students);
        course.setRatings(ratings);
        return course;
    }
}