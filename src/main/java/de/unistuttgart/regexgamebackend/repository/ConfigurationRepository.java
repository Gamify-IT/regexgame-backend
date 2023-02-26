package de.unistuttgart.regexgamebackend.repository;

import de.unistuttgart.regexgamebackend.data.Configuration;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConfigurationRepository extends JpaRepository<Configuration, UUID> {}
