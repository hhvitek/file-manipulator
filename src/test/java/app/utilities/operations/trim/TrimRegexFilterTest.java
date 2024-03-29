package app.utilities.operations.trim;

import app.utilities.string_filters.operations.trim.TrimRegexFilter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TrimRegexFilterTest extends TrimFilterTest {

    @BeforeEach
    void init() {
        operation = new TrimRegexFilter();
    }

    @Test
    void oneLetterFilterTest() {
        final String input = "aHello Worldaa";
        final String outputExpected = "Hello Worlda";

        operation.addFilter("a");

        final String outputActual = operation.filter(input);

        Assertions.assertEquals(outputExpected, outputActual);
    }

    @Test
    void oneLetterWithPlusSignFilterTest() {
        final String input = "aHello Worldaa";
        final String outputExpected = "Hello World";

        operation.addFilter("a+");

        final String outputActual = operation.filter(input);

        Assertions.assertEquals(outputExpected, outputActual);
    }

    @Test
    void oneSpaceWithPlusSignFilterTest() {
        final String input = "  Hello World   ";
        final String outputExpected = "Hello World";

        operation.addFilter(" +");

        final String outputActual = operation.filter(input);
        Assertions.assertEquals(outputExpected, outputActual);
    }

}