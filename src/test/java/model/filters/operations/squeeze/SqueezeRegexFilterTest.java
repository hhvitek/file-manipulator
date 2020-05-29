package model.filters.operations.squeeze;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SqueezeRegexFilterTest extends SqueezeFilterTest {

    @BeforeEach
    void init() {
        filter = new SqueezeRegexFilter();
    }

    //######################!!!!!!!!!
    //HOW SHOULD IT BEHAVE?
    @Test
    void twoFiltersOneLetterFilterTest() {
        final String input = "Hello Woorldddd";
        final String outputExpected = "Helo Worldddd"; // --OK?
        //final String outputExpected = "Hel Worldddd"; // --KO?

        filter.addNextFilter("l");
        filter.addNextFilter("o");

        final String outputActual = filter.filter(input);

        Assertions.assertEquals(outputExpected, outputActual);
    }

    @Test
    void oneFilterTwoCharacters() {
        final String input = "Hello Woorldddd";
        final String outputExpected = input;

        filter.addNextFilter("lo");

        final String outputActual = filter.filter(input);

        Assertions.assertEquals(outputExpected, outputActual);
    }
}
