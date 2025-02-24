package dtn.ServiceScore.repositories;

import dtn.ServiceScore.model.ExternalEvent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExternalEventRepository extends JpaRepository<ExternalEvent, Long> {
}
