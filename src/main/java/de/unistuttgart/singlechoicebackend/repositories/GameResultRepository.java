package de.unistuttgart.singlechoicebackend.repositories;

import de.unistuttgart.singlechoicebackend.data.GameResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameResultRepository extends JpaRepository<GameResult, Long> {}
