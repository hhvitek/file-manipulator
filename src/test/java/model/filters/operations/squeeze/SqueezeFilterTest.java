package model.filters.operations.squeeze;

import model.filters.Filter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public abstract class SqueezeFilterTest {

    protected Filter filter = new SqueezeCharacterFilter();

    @Test
    void noFilterShouldReturnInputUnchangedTest() {
        final String input = "Hello World";
        final String outputExpected = input;

        final String outputActual = filter.filter(input);

        Assertions.assertEquals(outputExpected, outputActual);
    }

    @Test
    void oneFilterOneLetterFilterTest() {
        final String input = "Hello World";
        final String outputExpected = "Helo World";

        filter.addNextFilter("l");

        final String outputActual = filter.filter(input);

        Assertions.assertEquals(outputExpected, outputActual);
    }





    @Test
    void emptyInputReturnsEmptyImput() {
        final String input = "";
        final String outputExpected = input;

        filter.addNextFilter("Hello");
        filter.addNextFilter("World");

        final String outputActual = filter.filter(input);

        Assertions.assertEquals(outputExpected, outputActual);
    }
}
