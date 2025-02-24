package dtn.ServiceScore.repositories;

import dtn.ServiceScore.model.Registration;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RegistrationRepository extends JpaRepository<Registration, Long> {
}
