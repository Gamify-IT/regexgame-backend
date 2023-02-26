package de.unistuttgart.regexgamebackend.data;

import java.util.EnumSet;
import java.util.UUID;
import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.hibernate.validator.constraints.Range;
import org.springframework.validation.annotation.Validated;

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
    EnumSet<RegexStructure> allowRegexStructures;

    /**
     * Game complexity factor.
     */
    @Range(min = 1)
    @Valid
    double complexity = 1;

    /**
     * Whether the complexity of the game should gradually increase with each new
     * challenge.
     */
    @Valid
    boolean increaseComplexity = true;

    /**
     * The amount of answers to be generated.
     */
    @Range(min = 2, max = 9)
    @Valid
    int answerCount = 3;
}
