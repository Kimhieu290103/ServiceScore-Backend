package dtn.ServiceScore.repositories;

import dtn.ServiceScore.model.Role;
import dtn.ServiceScore.model.Semester;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SemesterRepository extends JpaRepository<Semester, Long> {
    Optional<Semester> findByName(String name);
}
