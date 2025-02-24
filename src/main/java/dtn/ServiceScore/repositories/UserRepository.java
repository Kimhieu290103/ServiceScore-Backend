package dtn.ServiceScore.repositories;

import dtn.ServiceScore.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
