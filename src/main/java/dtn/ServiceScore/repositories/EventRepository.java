package dtn.ServiceScore.repositories;

import dtn.ServiceScore.model.Event;
import dtn.ServiceScore.model.EventType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long> {
    boolean existsByName(String name);
//    @Query(value = "SELECT * FROM events WHERE event_type_id = :eventTypeId",
//            countQuery = "SELECT COUNT(*) FROM events WHERE event_type_id = :eventTypeId",
//            nativeQuery = true)
//    Page<Event> findByEventTypeNative(@Param("eventTypeId") Long eventTypeId, Pageable pageable);
Page<Event> findByUser_Id(Long userId, Pageable pageable);

    List<Event> findByEndDateBeforeAndStatusNot(LocalDateTime endDate, String status);

    // Tìm sự kiện theo eventType.id và có phân trang
    Page<Event> findByEventType_Id(Long eventTypeId, Pageable pageable);
}
