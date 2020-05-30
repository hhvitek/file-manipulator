package model.filters.operations.blacklist;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BlacklistCharacterFilterTest extends BlacklistFilterTest {

    @BeforeEach
    void init() {
        filter = new BlacklistCharacterFilter();
    }

    @Test
    void oneWordFilterDoesUnderstandAsIndependentLettersTest() {
        final String input = "Hello World";
        final String outputExpected = "Wrd";

        filter.addNextFilter("Hello ");

        final String outputActual = filter.filter(input);

        Assertions.assertEquals(outputExpected, outputActual);
    }
}
