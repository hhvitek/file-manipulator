package model.filters;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Common tests that should be run by all Filter implementations....
 */
abstract class FilterTest {

    Filter filter;

    @Test
    void noFilterShouldReturnInputUnchangedTest() {
        final String input = "Hello World";
        final String outputExpected = input;

        final String outputActual = filter.filter(input);

        Assertions.assertEquals(outputExpected, outputActual);
    }

    @Test
    void oneLetterFilterTest() {
        final String input = "Hello World";
        final String outputExpected = "Heo Word";

        filter.addNextFilter("l");

        final String outputActual = filter.filter(input);

        Assertions.assertEquals(outputExpected, outputActual);
    }

    @Test
    void oneWordFilterTest() {
        final String input = "Hello World";
        final String outputExpected = "World";

        filter.addNextFilter("Hello ");

        final String outputActual = filter.filter(input);

        Assertions.assertEquals(outputExpected, outputActual);
    }

    @Test
    void oneLetterTwoFiltersTest() {
        final String input = "Hello World";
        final String outputExpected = "He Wrd";

        filter.addNextFilter("l")
                .addNextFilter("o");

        final String outputActual = filter.filter(input);

        Assertions.assertEquals(outputExpected, outputActual);
    }

    @Test
    void oneWordTwoFiltersTest() {
        final String input = "Hello World";
        final String outputExpected = " ";

        filter.addNextFilter("Hello")
                .addNextFilter("World");

        final String outputActual = filter.filter(input);

        Assertions.assertEquals(outputExpected, outputActual);
    }
}
