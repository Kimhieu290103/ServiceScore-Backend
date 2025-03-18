package dtn.ServiceScore.repositories;

import dtn.ServiceScore.model.Class;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ClassRepository extends JpaRepository<Class, Long> {
    Optional<Class> findByName(String name);

    List<Class> findByCourseId(Long courseId);

    List<Class> findByDepartmentId(Long departmentId);

    List<Class> findByDepartmentIdAndCourseId(Long departmentId, Long courseId);
}
