package de.unistuttgart.regexgamebackend.data;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.STRING)
public enum RegexStructure {
    /**
     * Simple character classes.
     *
     * [xyz]
     */
    CHARACTER_CLASS,

    /**
     * {@link #CHARACTER_CLASS} including ranges.
     *
     * [a-z]
     */
    CHARACTER_CLASS_RANGE,

    /**
     * The inverse of {@link #CHARACTER_CLASS}.
     *
     * [^xyz]
     */
    NEGATED_CHARACTER_CLASS,

    /**
     * Match a single character.
     *
     * .
     */
    SINGLE_CHARACTER_MATCH,
}
