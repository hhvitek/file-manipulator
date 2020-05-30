package model.string_filters.operations.blacklist;

import model.string_filters.operations.Operation;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Common tests that should be run by all Filter implementations....
 */
public abstract class BlacklistFilterTest {

    protected Operation operation;

    @Test
    void noFilterShouldReturnInputUnchangedTest() {
        final String input = "Hello World";
        final String outputExpected = input;

        final String outputActual = operation.filter(input);

        Assertions.assertEquals(outputExpected, outputActual);
    }

    @Test
    void oneLetterFilterTest() {
        final String input = "Hello World";
        final String outputExpected = "Heo Word";

        operation.addFilter("l");

        final String outputActual = operation.filter(input);

        Assertions.assertEquals(outputExpected, outputActual);
    }




}
