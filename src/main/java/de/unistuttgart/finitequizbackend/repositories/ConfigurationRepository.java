package de.unistuttgart.finitequizbackend.repositories;

import de.unistuttgart.finitequizbackend.data.Configuration;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConfigurationRepository extends JpaRepository<Configuration, UUID> {}
