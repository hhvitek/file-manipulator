package model.filters.operations.squeeze;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SqueezeCharacterFilterTest extends SqueezeFilterTest {

    @BeforeEach
    void init() {
        filter = new SqueezeCharacterFilter();
    }

    @Test
    void twoFiltersOneLetterFilterTest() {
        final String input = "Hello Woorldddd";
        final String outputExpected = "Helo Worldddd";

        filter.addNextFilter("l");
        filter.addNextFilter("o");

        final String outputActual = filter.filter(input);

        Assertions.assertEquals(outputExpected, outputActual);
    }

    @Test
    void oneFilterTwoCharacters() {
        final String input = "Hello Woorldddd";
        final String outputExpected = "Helo Worldddd";

        filter.addNextFilter("lo");

        final String outputActual = filter.filter(input);

        Assertions.assertEquals(outputExpected, outputActual);
    }

}
