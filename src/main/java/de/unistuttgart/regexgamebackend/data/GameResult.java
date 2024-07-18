package de.unistuttgart.regexgamebackend.data;

import java.util.UUID;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.hibernate.validator.constraints.Range;
import org.springframework.lang.NonNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class GameResult {

    final String game = "REGEXGAME"; // NOSONAR

    /**
     * id of the configuration
     */
    @NonNull
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

    /**
     * user id
     */
    @NonNull
    String userId;

    /**
     * Rewards gained after playing the game (coins)
     */
    @Range(min = 0)
    int rewards;
}
