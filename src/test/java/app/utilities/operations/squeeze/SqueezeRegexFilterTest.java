package app.utilities.operations.squeeze;

import app.utilities.string_filters.operations.squeeze.SqueezeRegexFilter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SqueezeRegexFilterTest extends SqueezeFilterTest {

    @BeforeEach
    void init() {
        operation = new SqueezeRegexFilter();
    }

    //######################!!!!!!!!!
    //HOW SHOULD IT BEHAVE?
    @Test
    void twoFiltersOneLetterFilter_SecondReplacesTheFirstOneTest() {
        final String input = "Hello Woorldddd";
        final String outputExpected = "Hello Worldddd";

        operation.addFilter("l");
        operation.addFilter("o");

        final String outputActual = operation.filter(input);

        Assertions.assertEquals(outputExpected, outputActual);
    }

    @Test
    void oneFilterTwoCharacters() {
        final String input = "Hello Woorldddd";
        final String outputExpected = input;

        operation.addFilter("lo");

        final String outputActual = operation.filter(input);

        Assertions.assertEquals(outputExpected, outputActual);
    }
}