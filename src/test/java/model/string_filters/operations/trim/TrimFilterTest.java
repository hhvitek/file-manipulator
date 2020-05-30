package model.string_filters.operations.trim;

import model.string_filters.operations.Operation;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

abstract public class TrimFilterTest {

    Operation operation;

    @Test
    void noFilterShouldBehaveAsSpaceTest() {
        final String input = "  Hello World   ";
        final String outputExpected = "Hello World";

        final String outputActual = operation.filter(input);
        Assertions.assertEquals(outputExpected, outputActual);
    }


}
