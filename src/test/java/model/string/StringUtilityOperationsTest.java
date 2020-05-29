package model.string;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.OS;

import java.util.regex.Pattern;

class StringUtilityOperationsTest {

    @Test
    void squeezeAllSpacesTest() {
        final String input = "     H   e         ll  o       Wor         l       d     ";
        final String outputExpected = " H e ll o Wor l d ";

        final String outputActual = StringUtilityOperations.squeezeWhat(input, ' ');

        Assertions.assertEquals(outputExpected, outputActual);
    }

    @Test
    void squeezeAllRepeatedCharactersTest() {
        final String input = "   JjjjjJ   kkkk  k   ";
        final String outputExpected = " JjJ k k ";

        final String outputActual = StringUtilityOperations.squeezeEverything(input);

        Assertions.assertEquals(outputExpected, outputActual);
    }

    @Test
    void squeezeAllWhitespacesTest() {
        final String input = "     H   e         ll  o       Wor         l       d     "
                + System.lineSeparator()
                + System.lineSeparator()
                + System.lineSeparator()
                + "\t\t\t\t\t";

        final String outputExpected = " H e ll o Wor l d ";

        final String outputActual = StringUtilityOperations.squeezeWhatRegex(input, Pattern.compile("\\s"));

        Assertions.assertEquals(outputExpected, outputActual);
    }

    @Test
    void squeezeTwoDifferentCharactersUsingRegexTest() {
        final String input = "aaXbbBBBcCCC";

        final String outputExpected = "aXbbBcCCC";

        final String outputActual = StringUtilityOperations.squeezeWhatRegex(input, Pattern.compile("[aB]"));

        Assertions.assertEquals(outputExpected, outputActual);
    }

    @Test
    void squeezeTwoDifferentCharactersUsingTwoMethodCallsWithoutRegexTest() {
        final String input = "aaXbbBBBcCCC";

        final String outputExpected = "aXbbBcCCC";

        final String inputSqueezedBy_a = StringUtilityOperations.squeezeWhat(input, 'a');
        final String inputSqueezedBy_a_B = StringUtilityOperations.squeezeWhat(inputSqueezedBy_a, 'B');

        final String outputActual = inputSqueezedBy_a_B;

        Assertions.assertEquals(outputExpected, outputActual);
    }

    @Test
    void squeezeAllWhitespacesToUnderscoreTest() {
        final String input = "     H   e         ll  o       Wor         l       d     "
                + System.lineSeparator()
                + System.lineSeparator()
                + System.lineSeparator()
                + "\t\t\t\t\t";

        final String outputExpected = "_H_e_ll_o_Wor_l_d_";

        final String outputWhitespacesSqueezed = StringUtilityOperations.squeezeWhatRegex(input, Pattern.compile("\\s"));
        final String outputActual = StringUtilityOperations.replaceWhatTo(outputWhitespacesSqueezed, " ", "_");

        Assertions.assertEquals(outputExpected, outputActual);
    }

    @Test
    void bugSqueezeLetterOFollowedBySpace() {
        final String input = "Hello Woorldddd";
        final String outputExpected = "Hello Worldddd";

        final String outputActual = StringUtilityOperations.squeezeWhatRegex(input, Pattern.compile("o"));

        Assertions.assertEquals(outputExpected, outputActual);
    }

    @Test
    @EnabledOnOs({OS.WINDOWS})
    void replaceAllWhitespacesForUnderscoreWindowsOSTest() {
        final String input = "     H   e         ll  o       Wor         l       d"
                + System.lineSeparator() // on Windows contains two characters \r\n
                + System.lineSeparator()
                + System.lineSeparator();

        final String outputExpected = "_____H___e_________ll__o_______Wor_________l_______d______";

        final String outputActual = StringUtilityOperations.replaceWhatRegexTo(input, Pattern.compile("\\s"), "_");

        Assertions.assertEquals(outputExpected, outputActual);
    }

    @Test
    @EnabledOnOs({OS.LINUX})
    void replaceAllWhitespacesForUnderscoreLinuxOSTest() {
        final String input = "     H   e         ll  o       Wor         l       d"
                + System.lineSeparator() // on Windows contains two characters \r\n
                + System.lineSeparator()
                + System.lineSeparator();

        final String outputExpected = "_____H___e_________ll__o_______Wor_________l_______d___";

        final String outputActual = StringUtilityOperations.replaceWhatRegexTo(input, Pattern.compile("\\s"), "_");

        Assertions.assertEquals(outputExpected, outputActual);
    }

    @Test
    void replaceAllSpacesForUnderscoreTest() {
        final String input = "     H   e         ll  o       Wor         l       d     "
                + System.lineSeparator()
                + System.lineSeparator()
                + System.lineSeparator()
                + "\t\t\t\t\t";

        final String outputExpected = "_____H___e_________ll__o_______Wor_________l_______d_____"
                + System.lineSeparator()
                + System.lineSeparator()
                + System.lineSeparator()
                + "\t\t\t\t\t";

        final String outputActual = StringUtilityOperations.replaceWhatTo(input, " ", "_");

        Assertions.assertEquals(outputExpected, outputActual);
    }

    @Test
    void replaceAllLineBreaksForUnderscoreTest() {
        final String input = " Hello World "
                + System.lineSeparator()
                + System.lineSeparator()
                + System.lineSeparator()
                + "\t\t\t\t\t";

        final String outputExpected = " Hello World "
                + "___"
                + "\t\t\t\t\t";

        final String outputActual = StringUtilityOperations.replaceWhatRegexTo(input, Pattern.compile(System.lineSeparator()), "_");

        Assertions.assertEquals(outputExpected, outputActual);
    }

}
