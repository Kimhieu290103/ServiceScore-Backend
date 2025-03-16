package dtn.ServiceScore.repositories;

import dtn.ServiceScore.model.Class;
import dtn.ServiceScore.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByUsername(String username);

    Optional<User> findByUsername(String username);

    List<User> findByClazz(Class clazzId);

    @Query("SELECT u FROM User u WHERE " +
            "(:classId IS NULL OR u.clazz.id = :classId) AND " +
            "(:courseId IS NULL OR u.clazz.course.id = :courseId) AND " +
            "(:departmentId IS NULL OR u.clazz.department.id = :departmentId)")
    List<User> findByFilters(@Param("classId") Long classId,
                             @Param("courseId") Integer courseId,
                             @Param("departmentId") Integer departmentId);

}
