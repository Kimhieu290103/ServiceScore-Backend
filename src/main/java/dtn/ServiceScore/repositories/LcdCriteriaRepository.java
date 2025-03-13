package dtn.ServiceScore.repositories;

import dtn.ServiceScore.model.FiveGoodCriteriaLcd;
import dtn.ServiceScore.model.LcdCriteria;
import dtn.ServiceScore.model.Semester;
import dtn.ServiceScore.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LcdCriteriaRepository extends JpaRepository<LcdCriteria, Long> {
    @Query("SELECT lc.user FROM LcdCriteria lc " +
            "WHERE lc.semester.id = :semesterId " +
            "GROUP BY lc.user " +
            "HAVING COUNT(DISTINCT lc.fiveGoodCriteriaLcd.id) = " +
            "(SELECT COUNT(f) FROM FiveGoodCriteriaLcd f)")
    List<User> findLcdsCompletedAllCriteria(@Param("semesterId") Long semesterId);


    @Query("SELECT COUNT(l) > 0 FROM LcdCriteria l WHERE l.user = :user AND l.fiveGoodCriteriaLcd = :criteria AND l.semester = :semester")
    boolean existsByUserAndCriteriaAndSemester(@Param("user") User user,
                                               @Param("criteria") FiveGoodCriteriaLcd criteria,
                                               @Param("semester") Semester semester);
}
