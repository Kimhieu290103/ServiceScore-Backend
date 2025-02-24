package dtn.ServiceScore.repositories;

import dtn.ServiceScore.model.Department;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository  extends JpaRepository<Department, Long> {
}
