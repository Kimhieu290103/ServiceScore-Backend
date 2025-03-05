package dtn.ServiceScore.repositories;

import dtn.ServiceScore.model.LcdCriteria;
import dtn.ServiceScore.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LcdCriteriaRepository extends JpaRepository<LcdCriteria, Long> {
    @Query("SELECT lc.user FROM LcdCriteria lc " +
            "GROUP BY lc.user " +
            "HAVING COUNT(DISTINCT lc.fiveGoodCriteriaLcd.id) = " +
            "(SELECT COUNT(f) FROM FiveGoodCriteriaLcd f)")
    List<User> findLcdsCompletedAllCriteria();
}
