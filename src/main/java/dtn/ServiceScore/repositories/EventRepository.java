package dtn.ServiceScore.repositories;

import dtn.ServiceScore.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, Long> {
    boolean existsByName(String name);
}
