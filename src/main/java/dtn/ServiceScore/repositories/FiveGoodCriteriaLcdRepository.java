package dtn.ServiceScore.repositories;

import dtn.ServiceScore.model.FiveGoodCriteriaLcd;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FiveGoodCriteriaLcdRepository extends JpaRepository<FiveGoodCriteriaLcd, Long> {
    List<FiveGoodCriteriaLcd> findByIsActiveTrue();
}
