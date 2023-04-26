package de.unistuttgart.regexgamebackend.data;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * This enumerates all possible RegexStructures
 * They are documented in the frontend.
 */
@JsonFormat(shape = JsonFormat.Shape.STRING)
public enum RegexStructure {
    SINGLE_CHARACTER,
    CHARACTER_SEQUENCE,
    ANY_SINGLE_CHARACTER,
    GROUP,
    CHARACTER_CLASS,
    DISJUNCTION,
    ANY_AMOUNT_QUANTIFIER,
    AT_LEAST_ONE_QUANTIFIER,
    OPTIONAL_QUANTIFIER,
    CHARACTER_CLASS_INVERTED,
    ABSOLUTE_NUMERIC_QUANTIFIER,
}
