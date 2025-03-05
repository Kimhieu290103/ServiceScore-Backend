package dtn.ServiceScore.repositories;

import dtn.ServiceScore.model.FiveGoodCriteria;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FiveGoodCriteriaRepository extends JpaRepository<FiveGoodCriteria, Long> {
    Optional<FiveGoodCriteria> findByName(String name);
}
