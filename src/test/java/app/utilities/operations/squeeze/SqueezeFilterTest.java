package app.utilities.operations.squeeze;

import app.utilities.string_filters.operations.Operation;
import app.utilities.string_filters.operations.squeeze.SqueezeCharacterFilter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public abstract class SqueezeFilterTest {

    protected Operation operation = new SqueezeCharacterFilter();

    @Test
    void noFilterShouldReturnInputUnchangedTest() {
        final String input = "Hello World";
        final String outputExpected = input;

        final String outputActual = operation.filter(input);

        Assertions.assertEquals(outputExpected, outputActual);
    }

    @Test
    void oneFilterOneLetterFilterTest() {
        final String input = "Hello World";
        final String outputExpected = "Helo World";

        operation.addFilter("l");

        final String outputActual = operation.filter(input);

        Assertions.assertEquals(outputExpected, outputActual);
    }





    @Test
    void emptyInputReturnsEmptyImput() {
        final String input = "";
        final String outputExpected = input;

        operation.addFilter("Hello");
        operation.addFilter("World");

        final String outputActual = operation.filter(input);

        Assertions.assertEquals(outputExpected, outputActual);
    }
}