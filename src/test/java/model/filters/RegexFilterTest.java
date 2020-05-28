package model.filters;

import model.filters.regex.RegexFilter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.regex.PatternSyntaxException;

public class RegexFilterTest extends FilterTest {

    @BeforeEach
    void init() {
        filter = new RegexFilter();
    }

    @Test
    void allSpacesFilterTest() {
        final String input = "    He   l   l  o W    or    ld    ";
        final String outputExpected = "HelloWorld";

        filter.addNextFilter(" ");

        final String outputActual = filter.filter(input);

        Assertions.assertEquals(outputExpected, outputActual);
    }

    @Test
    void allWhitespacesFilterTest() {
        final String input = "    He   l   l  o W    or    ld    "
                + System.lineSeparator()
                + System.lineSeparator()
                + System.lineSeparator()
                + "\t\t\t\t\t";
        final String outputExpected = "HelloWorld";

        filter.addNextFilter("\\s");

        final String outputActual = filter.filter(input);

        Assertions.assertEquals(outputExpected, outputActual);
    }

    @Test
    void onlyTwoAndMoreConsequtiveWhitespacesFilterTest() {
        final String input = "    He l l o    W    or    ld    "
                + System.lineSeparator()
                + System.lineSeparator()
                + System.lineSeparator()
                + "\t\t\t\t\t";
        final String outputExpected = "He l l oWorld";

        filter.addNextFilter("\\s{2,}");

        final String outputActual = filter.filter(input);

        Assertions.assertEquals(outputExpected, outputActual);
    }

    @Test
    void malformedFilterRegexThrowsPatternException() {
        final String input = "Hello World";

        filter.addNextFilter("\\s");

        Assertions.assertThrows(
                PatternSyntaxException.class,
                () -> filter.addNextFilter("\\s{}")
        );
    }
}
