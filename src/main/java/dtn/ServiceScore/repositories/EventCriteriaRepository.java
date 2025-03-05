package dtn.ServiceScore.repositories;

import dtn.ServiceScore.model.EventCriteria;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EventCriteriaRepository extends JpaRepository<EventCriteria, Long> {
    @Modifying
    @Transactional
    @Query("DELETE FROM EventCriteria  ecl WHERE ecl.event.id = :eventId")
    void deleteByEventId(@Param("eventId") Long id);

    @Query("SELECT ecl FROM EventCriteria ecl WHERE ecl.event.id = :eventId")
    List<EventCriteria> findByEventId(@Param("eventId") Long id);
}
