package de.unistuttgart.regexgamebackend.data;

import java.util.UUID;

import org.hibernate.validator.constraints.Range;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class GameResult {

    final String game = "REGEXGAME"; // NOSONAR

    /**
     * id of the configuration
     */
    UUID configurationId;

    /**
     * the completion score
     */
    @Range(min = 0, max = 100)
    long score;

    /**
     * amount of completed rounds
     */
    @Range(min = 0)
    int completedRounds;

}
