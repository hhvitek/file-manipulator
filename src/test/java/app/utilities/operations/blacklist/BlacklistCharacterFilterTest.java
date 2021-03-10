package app.utilities.operations.blacklist;

import app.utilities.string_filters.operations.blacklist.BlacklistCharacterFilter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BlacklistCharacterFilterTest extends BlacklistFilterTest {

    @BeforeEach
    void init() {
        operation = new BlacklistCharacterFilter();
    }

    @Test
    void oneWordFilterDoesUnderstandAsIndependentLettersTest() {
        final String input = "Hello World";
        final String outputExpected = "Wrd";

        operation.addFilter("Hello ");

        final String outputActual = operation.filter(input);

        Assertions.assertEquals(outputExpected, outputActual);
    }

    @Test
    void oneWordTwoFiltersTest() {
        final String input = "Hello World";
        final String outputExpected = " ";

        operation.addFilter("Hello");
        operation.addFilter("World");

        final String outputActual = operation.filter(input);

        Assertions.assertEquals(outputExpected, outputActual);
    }

    @Test
    void oneLetterTwoFiltersTest() {
        final String input = "Hello World";
        final String outputExpected = "He Wrd";

        operation.addFilter("l");
        operation.addFilter("o");

        final String outputActual = operation.filter(input);

        Assertions.assertEquals(outputExpected, outputActual);
    }
}