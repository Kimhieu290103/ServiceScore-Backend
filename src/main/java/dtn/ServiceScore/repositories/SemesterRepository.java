package dtn.ServiceScore.repositories;

import dtn.ServiceScore.model.Semester;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SemesterRepository extends JpaRepository<Semester, Long> {
}
