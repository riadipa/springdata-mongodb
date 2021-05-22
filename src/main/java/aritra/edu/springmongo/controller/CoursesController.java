package aritra.edu.springmongo.controller;

import aritra.edu.springmongo.document.Course;
import aritra.edu.springmongo.repo.CoursesRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/courses")
public class CoursesController {

    Logger logger = LoggerFactory.getLogger(CoursesController.class);

    @Autowired
    private CoursesRepository repository;

    /**
     * @return newly created course document
     */
    @RequestMapping(method = RequestMethod.POST, value = "/")
    public ResponseEntity<Course> saveCourse(@RequestBody Course course) {
        logger.info("in courses controller /");
        Course savedCourse = repository.save(course);
        if (savedCourse != null) {
            return new ResponseEntity<>(savedCourse, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * @return list of course documents
     */
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<Course>> getCourses() {
        logger.info("in courses controller /");
        List<Course> courses = repository.findAll();
        return new ResponseEntity<>(courses, HttpStatus.OK);

    }

    /**
     * @param id
     * @return course document based on id
     */
    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    public ResponseEntity<Course> getCourseById(@PathVariable String id) {
        logger.info("in courses controller /{id}");
        Optional<Course> course = repository.findById(id);
        if (course.isPresent()) {
            return new ResponseEntity<>(course.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * @param id
     * @return confirmation after deletion based on id
     */
    @RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
    public ResponseEntity<Course> deleteCourseById(@PathVariable String id) {
        logger.info("in courses controller /{id}");
        repository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
