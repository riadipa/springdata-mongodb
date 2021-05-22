package aritra.edu.springmongo.service;

import aritra.edu.springmongo.document.Course;
import aritra.edu.springmongo.document.Student;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.List;

@Service
public class ImportData {

    Logger logger = LoggerFactory.getLogger(ImportData.class);

    @Autowired
    private MongoTemplate mongoTemplate;

    private static final String COLLECTION_STUDENT = "students";
    private static final String COLLECTION_COURSE = "courses";

    public void importData() {
        clearAll();
        importStudents();
        importCourses();
    }

    public void clearAll() {
        logger.info("Clearing all data");
        mongoTemplate.remove(new Query(), COLLECTION_STUDENT);
        mongoTemplate.remove(new Query(), COLLECTION_COURSE);
    }

    private void importStudents() {

        logger.info("Creating collection : students");

        ObjectMapper mapper = new ObjectMapper();

        TypeReference<List<Student>> typeReference = new TypeReference<List<Student>>() {
        };
        try {
            InputStream inputStream = new ClassPathResource("json/students.json").getInputStream();
            List<Student> students = mapper.readValue(inputStream, typeReference);

            if (!mongoTemplate.collectionExists(Student.class)) {
                mongoTemplate.createCollection(Student.class);
            }

            logger.info("importing data in students collection");

            mongoTemplate.insert(students, COLLECTION_STUDENT);

        } catch (Exception e) {
            logger.info("Unable to save students: " + e.getMessage());
        }

    }

    private void importCourses() {

        logger.info("Creating collection : courses");

        ObjectMapper mapper = new ObjectMapper();

        TypeReference<List<Course>> typeReference = new TypeReference<List<Course>>() {
        };
        try {
            InputStream inputStream = new ClassPathResource("json/courses.json").getInputStream();
            List<Course> courses = mapper.readValue(inputStream, typeReference);

            if (!mongoTemplate.collectionExists(Course.class)) {
                mongoTemplate.createCollection(Course.class);
            }

            logger.info("importing data in courses collection");

            mongoTemplate.insert(courses, COLLECTION_COURSE);

        } catch (Exception e) {
            logger.info("Unable to save users: " + e.getMessage());
        }

    }
}
