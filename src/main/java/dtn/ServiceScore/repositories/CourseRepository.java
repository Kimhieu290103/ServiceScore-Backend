package dtn.ServiceScore.repositories;

import dtn.ServiceScore.model.Course;
import dtn.ServiceScore.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CourseRepository extends JpaRepository<Course, Long> {
    Optional<Course> findByName(String name);
}
