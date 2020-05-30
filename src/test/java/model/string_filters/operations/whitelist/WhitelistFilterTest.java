package model.string_filters.operations.whitelist;

import model.string_filters.operations.Operation;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public abstract class WhitelistFilterTest {

    protected Operation operation;

    @Test
    void oneLetterTest() {
        final String input = "Hello World";
        final String expectedOutput = "oo";

        final String filteredCharacter = "o";
        operation.addFilter(filteredCharacter);

        final String actualOutput = operation.filter(input);

        Assertions.assertEquals(expectedOutput, actualOutput);
    }

    @Test
    void twoLettersTest() {
        final String input = "Hello World";
        final String expectedOutput = "llool";

        final String filteredCharacter = "lo";
        operation.addFilter(filteredCharacter);

        final String actualOutput = operation.filter(input);

        Assertions.assertEquals(expectedOutput, actualOutput);
    }


}
