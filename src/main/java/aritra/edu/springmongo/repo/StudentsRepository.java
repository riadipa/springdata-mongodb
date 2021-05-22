package aritra.edu.springmongo.repo;

import aritra.edu.springmongo.document.Student;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface StudentsRepository extends MongoRepository<Student, String> {
    Optional<Student> findById(String id);

    List<Student> findAll();
}

