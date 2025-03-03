package dtn.ServiceScore.repositories;

import dtn.ServiceScore.model.Registration;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RegistrationRepository extends JpaRepository<Registration, Long> {
    List<Registration> findByUserId(Long userId);
    List<Registration> findByEventId(Long eventId);
    List<Registration> findByUserIdAndEventId(Long userId, Long eventId);
}
