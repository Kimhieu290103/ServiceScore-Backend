package dtn.ServiceScore.repositories;

import dtn.ServiceScore.model.Event;
import dtn.ServiceScore.model.Registration;
import dtn.ServiceScore.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RegistrationRepository extends JpaRepository<Registration, Long> {
    List<Registration> findByUser(User userId);
    List<Registration> findByEvent(Event eventId);
    List<Registration> findByUserIdAndEventId(Long userId, Long eventId);



}
