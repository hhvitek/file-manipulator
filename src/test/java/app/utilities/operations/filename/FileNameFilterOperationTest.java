package app.utilities.operations.filename;

import app.utilities.string_filters.FilterException;
import app.utilities.string_filters.operations.Operation;
import app.utilities.string_filters.operations.filename.FileNameFilterOperation;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class FileNameFilterOperationTest {
    Operation operation = new FileNameFilterOperation();

    @Test
    void tryingToAddFilter_ResultsInExceptionTest() {
        Assertions.assertThrows(
                FilterException.class,
                () -> operation.addFilter("newFilter")
        );
    }

    @Test
    void filenameOneSqueezeAndReplaceSpaceTest() {
        final String input = "Path__to_1566_year (-1567)";
        final String outputExpected = "Path_to_1566_year_(-1567)";

        final String outputActual = operation.filter(input);

        Assertions.assertEquals(outputExpected, outputActual);
    }

    @Test
    void filenameTwoContainsPlusSignTest() {
        final String input = "Path_to_1566_year (+-1567)";
        final String outputExpected = "Path_to_1566_year_(+-1567)";

        final String outputActual = operation.filter(input);

        Assertions.assertEquals(outputExpected, outputActual);
    }

    @Test
    void filenameSpacesAtTheBeginningTest() {
        final String input = "    Path_to_1566_year (+-1567)";
        final String outputExpected = "Path_to_1566_year_(+-1567)";

        final String outputActual = operation.filter(input);

        Assertions.assertEquals(outputExpected, outputActual);
    }

    @Test
    void filenameUnderscoreAtTheBeginningTest() {
        final String input = "_Path_to_1566_year (+-1567)";
        final String outputExpected = "Path_to_1566_year_(+-1567)";

        final String outputActual = operation.filter(input);

        Assertions.assertEquals(outputExpected, outputActual);
    }
}