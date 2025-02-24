package dtn.ServiceScore.repositories;

import dtn.ServiceScore.model.EventCriteria;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventCriteriaRepository extends JpaRepository<EventCriteria, Long> {
}
