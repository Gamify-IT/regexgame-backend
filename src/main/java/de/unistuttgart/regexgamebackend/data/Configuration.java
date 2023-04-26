package de.unistuttgart.regexgamebackend.data;

import java.util.EnumSet;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Range;
import org.springframework.lang.Nullable;
import org.springframework.validation.annotation.Validated;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

/**
 * Contains all data that have to be stored to configure a regex game.
 */
@Entity
@Data
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Validated
public class Configuration {

    /**
     * A unique identifier for the configuration.
     */
    @Id
    @GeneratedValue(generator = "uuid")
    UUID id;

    /**
     * Allowed regex structures for this game.
     */
    @Size(min = 1)
    @NotNull
    EnumSet<RegexStructure> allowedRegexStructures;

    /**
     * minimum required rounds for the game to be counted as completed
     * if null the default defined in the frontend will be used
     */
    @Nullable
    Integer minimumCompletedRounds;

    /**
     * how many answers to show in each riddle
     * if null the default defined in the frontend will be used
     */
    @Nullable
    Integer answerCount;

    /**
     * how many seconds the user has to solve a given riddle
     * the timeout will accumulate over the riddles,
     * so the remaining time will be carried over to the next riddle
     * if zero there is no timeout
     * if null the default defined in the frontend will be used
     */
    @Nullable
    @Range(min = 0)
    Integer riddleTimeoutSeconds;
}
