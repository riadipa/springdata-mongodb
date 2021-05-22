package aritra.edu.springmongo.controller;

import aritra.edu.springmongo.document.Student;
import aritra.edu.springmongo.repo.StudentsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/students")
public class StudentController {

    Logger logger = LoggerFactory.getLogger(CoursesController.class);

    @Autowired
    private StudentsRepository repository;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<Student>> getStudents() {

        logger.info("in student controller /");

        List<Student> students = repository.findAll();
        return new ResponseEntity<>(students, HttpStatus.OK);

    }

    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    public ResponseEntity<Student> getStudentById(@PathVariable String id) {
        logger.info("in student controller /{id}");
        Optional<Student> student = repository.findById(id);
        if (student.isPresent()) {
            return new ResponseEntity<>(student.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

}
