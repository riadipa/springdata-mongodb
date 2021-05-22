package aritra.edu.springmongo.controller;

import aritra.edu.springmongo.document.Course;
import aritra.edu.springmongo.repo.CoursesRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Optional;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(CoursesController.class)
public class CoursesControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CoursesRepository coursesRepository;

    @Test
    public void getCourses() {
    }

    @Test
    public void saveCourses() throws Exception {
        Course course = saveCourse();
        Mockito.when(coursesRepository.save(course)).thenReturn(course);
        mockMvc.perform(MockMvcRequestBuilders
                .post("/courses/")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    public void getCourseById() throws Exception {
        Optional<Course> course = createCourse();
        Mockito.when(coursesRepository.findById("1")).thenReturn(course);

        mockMvc.perform(MockMvcRequestBuilders
                .get("/courses/{id}", "1")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(course.get().getName()));

        mockMvc.perform(MockMvcRequestBuilders
                .get("/courses/{id}", "2")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void deleteCourseById() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .delete("/courses/{id}", "1")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    private Optional<Course> createCourse() {
        String[] students = {"Paul", "Parker"};
        String[] ratings = {"5.0", "4.1", "3.5"};
        Course course = new Course();
        course.setId("1");
        course.setName("History");
        course.setDescription("Make a big deal");
        course.setStudents(students);
        course.setRatings(ratings);
        return Optional.of(course);
    }

    private Course saveCourse() {
        String[] students = {"Arthur", "Peter"};
        String[] ratings = {"5.0", "4.1", "3.5"};
        Course course = new Course();
        course.setId("3");
        course.setName("Geography");
        course.setDescription("Do not make a big deal");
        course.setStudents(students);
        course.setRatings(ratings);
        return course;
    }
}