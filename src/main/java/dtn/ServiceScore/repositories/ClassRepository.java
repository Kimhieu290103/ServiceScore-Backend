package dtn.ServiceScore.repositories;

import dtn.ServiceScore.model.Class;
import dtn.ServiceScore.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClassRepository extends JpaRepository<Class, Long> {
    Optional<Class> findByName(String name);
}
