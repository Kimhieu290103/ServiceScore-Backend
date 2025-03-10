package dtn.ServiceScore.repositories;

import dtn.ServiceScore.model.Event;
import dtn.ServiceScore.model.EventType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long> {
    boolean existsByName(String name);
    Page<Event> findByEventType_Id(Long eventTypeId, Pageable pageable);
    List<Event> findByUser_Id(Long userId);

}
