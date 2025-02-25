package dtn.ServiceScore.repositories;

import dtn.ServiceScore.model.Lcd;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LcdRepository  extends JpaRepository<Lcd, Long> {
    Optional<Lcd> findByName(String name);
}
