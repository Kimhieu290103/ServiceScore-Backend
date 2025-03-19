package dtn.ServiceScore.repositories;

import dtn.ServiceScore.model.EventCriteriaLcd;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EventCriteriaLcdRepository extends JpaRepository<EventCriteriaLcd, Long> {
    @Modifying
    @Transactional
    @Query("DELETE FROM EventCriteriaLcd ecl WHERE ecl.event.id = :eventId")
    void deleteByEventId(@Param("eventId") Long id);

    // ✅ **Tìm tất cả bản ghi theo eventId**
    @Query("SELECT ecl FROM EventCriteriaLcd ecl WHERE ecl.event.id = :eventId")
    List<EventCriteriaLcd> findByEventId(@Param("eventId") Long id);

    @Transactional
    @Modifying
    @Query("DELETE FROM EventCriteriaLcd e WHERE e.criteria.id = :criteriaId")
    void deleteByCriteriaId(Long criteriaId);
}
