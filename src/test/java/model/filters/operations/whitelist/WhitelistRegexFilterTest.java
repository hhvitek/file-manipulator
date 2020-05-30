package model.filters.operations.whitelist;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class WhitelistRegexFilterTest extends WhitelistFilterTest {

    @BeforeEach
    void init() {
        filter = new WhitelistRegexFilter();
    }

    @Test
    void oneLetterOneFiltersWhitespacesTest() {
        final String input = "Hello World"
                + System.lineSeparator()
                + System.lineSeparator()
                + "\t\t\t\t";
        final String expectedOutput = " "
                + System.lineSeparator()
                + System.lineSeparator()
                + "\t\t\t\t";

        filter.addNextFilter("\\s");

        final String actualOutput = filter.filter(input);

        Assertions.assertEquals(expectedOutput, actualOutput);
    }

    @Test
    void oneComplexRegexFilters_AllAlphaNumericANDUnderscoresTest() {
        final String input = "_Hello World 1453_"
                + System.lineSeparator()
                + System.lineSeparator()
                + "\t\t\t\t";
        final String expectedOutput = "_HelloWorld1453_";

        filter.addNextFilter("[\\w_\\d]");

        final String actualOutput = filter.filter(input);

        Assertions.assertEquals(expectedOutput, actualOutput);
    }
}
