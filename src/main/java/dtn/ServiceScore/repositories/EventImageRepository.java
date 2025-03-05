package dtn.ServiceScore.repositories;

import dtn.ServiceScore.model.EventImage;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EventImageRepository extends JpaRepository<EventImage, Long> {
    List<EventImage> findByEventId(Long eventId);

    @Modifying
    @Transactional
    @Query("DELETE FROM EventImage  ecl WHERE ecl.event.id = :eventId")
    void deleteByEventId(@Param("eventId") Long id);
}
