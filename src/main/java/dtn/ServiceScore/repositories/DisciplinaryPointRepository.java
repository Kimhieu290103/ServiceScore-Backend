package dtn.ServiceScore.repositories;

import dtn.ServiceScore.model.DisciplinaryPoint;
import dtn.ServiceScore.model.Registration;
import dtn.ServiceScore.model.Semester;
import dtn.ServiceScore.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DisciplinaryPointRepository  extends JpaRepository<DisciplinaryPoint, Long> {
    DisciplinaryPoint findByUserAndSemester(User user, Semester semester);
}
