package dtn.ServiceScore.repositories;

import dtn.ServiceScore.model.Event;
import dtn.ServiceScore.model.Registration;
import dtn.ServiceScore.model.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RegistrationRepository extends JpaRepository<Registration, Long> {
    List<Registration> findByUser(User userId);

    List<Registration> findByEvent(Event eventId);

    List<Registration> findByUserIdAndEventId(Long userId, Long eventId);

    Registration findByUserAndEvent(User user, Event event);

    @Query("SELECT r FROM Registration r WHERE r.user.id = :userId")
    List<Registration> findByUserId(@Param("userId") Long userId);

    @Query("SELECT r FROM Registration r WHERE r.event.id = :eventId")
    List<Registration> findByEventId(@Param("eventId") Long eventId);

    @Modifying
    @Transactional
    @Query("DELETE FROM Registration r WHERE r.user.id = :userId AND r.event.id = :eventId")
    void deleteByUserIdAndEventId(@Param("userId") Long userId, @Param("eventId") Long eventId);

    List<Registration> findByEventAndAttendancesTrue(Event event);
}
