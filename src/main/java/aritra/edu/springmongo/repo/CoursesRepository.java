package aritra.edu.springmongo.repo;

import aritra.edu.springmongo.document.Course;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface CoursesRepository extends MongoRepository<Course, String> {
    Optional<Course> findById(String id);

    List<Course> findAll();
}
