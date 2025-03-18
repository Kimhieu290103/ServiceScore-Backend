package dtn.ServiceScore.repositories;

import dtn.ServiceScore.model.ExternalEvent;
import dtn.ServiceScore.utils.Enums.ExternalEventStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExternalEventRepository extends JpaRepository<ExternalEvent, Long> {
    List<ExternalEvent> findByStatus(ExternalEventStatus status);

    List<ExternalEvent> findByUserId(Long userId);
}
