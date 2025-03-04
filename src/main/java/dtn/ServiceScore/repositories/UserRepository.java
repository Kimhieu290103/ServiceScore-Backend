package dtn.ServiceScore.repositories;

import dtn.ServiceScore.model.Class;
import dtn.ServiceScore.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByUsername(String username);

    Optional<User> findByUsername (String username);

    List<User> findByClazz(Class clazzId);
}
