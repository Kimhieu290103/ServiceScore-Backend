package dtn.ServiceScore.repositories;

import dtn.ServiceScore.model.StudentCriteria;
import dtn.ServiceScore.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StudentCriteriaRepository extends JpaRepository<StudentCriteria, Long> {
    @Query("SELECT COUNT(DISTINCT sc.criteria.id) FROM StudentCriteria sc WHERE sc.student.id = :userId")
    long countDistinctCriteriaByUser(@Param("userId") Long userId);

    @Query("SELECT sc.student FROM StudentCriteria sc " +
            "WHERE sc.semester.id = :semesterId " +
            "GROUP BY sc.student " +
            "HAVING COUNT(DISTINCT sc.criteria.id) = 5")
    List<User> findStudentsCompletedAllCriteria(@Param("semesterId") Long semesterId);
}
