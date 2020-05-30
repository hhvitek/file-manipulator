package model.filters.operations.whitelist;

import model.filters.Filter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public abstract class WhitelistFilterTest {

    protected Filter filter;

    @Test
    void oneLetterTest() {
        final String input = "Hello World";
        final String expectedOutput = "oo";

        final String filteredCharacter = "o";
        filter.addNextFilter(filteredCharacter);

        final String actualOutput = filter.filter(input);

        Assertions.assertEquals(expectedOutput, actualOutput);
    }

    @Test
    void twoLettersTest() {
        final String input = "Hello World";
        final String expectedOutput = "llool";

        final String filteredCharacter = "lo";
        filter.addNextFilter(filteredCharacter);

        final String actualOutput = filter.filter(input);

        Assertions.assertEquals(expectedOutput, actualOutput);
    }

    @Test
    void oneLetterTwoFiltersTest() {
        final String input = "Hello World";
        final String expectedOutput = "llool";

        filter.addNextFilter("o");
        filter.addNextFilter("l");

        final String actualOutput = filter.filter(input);

        Assertions.assertEquals(expectedOutput, actualOutput);
    }
}
