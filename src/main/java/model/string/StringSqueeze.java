package model.string;

import org.jetbrains.annotations.NotNull;

/**
 * Abstract class for squeeze operations.
 *
 * Squeezable sequence of character is defined as a consecutive sequence of the relevant characters
 *
 * In it's basic form relevant characters are the SAME characters:
 * => It's char operation (char a == char b) returns true
 *
 * For example.
 * Input: aaXbbBBBcCCC
 * Output: aXbBcC
 */
public abstract class StringSqueeze {

    private StringBuilder output;

    protected StringSqueeze() {
    }

    public String squeeze(@NotNull String input) {
        this.output = createOutputStringBuilderWithMaxPossibleSize(input);

        if (inputTooSmall(input)) {
            return input;
        }

        performSqueeze(input);

        return output.toString();
    }

    private StringBuilder createOutputStringBuilderWithMaxPossibleSize(@NotNull String input) {
        return new StringBuilder(input.length());
    }

    private boolean inputTooSmall(@NotNull String input) {
        if (input.length() < 2) {
            return true;
        }
        return false;
    }

    private void performSqueeze(@NotNull String input) {
        char[] inputAsChars = input.toCharArray();

        char theFirstCharacter = getFirstChar(inputAsChars);
        output.append(theFirstCharacter); // always append the first character

        char lastAddedChar = theFirstCharacter;
        for (int i = 1; i < inputAsChars.length; i++) {
            char currentChar = inputAsChars[i];
            if (shouldAppendChar(currentChar, lastAddedChar)) {
                output.append(currentChar);
                lastAddedChar = currentChar;
            }
        }
    }

    protected abstract boolean shouldAppendChar(char currentChar, char lastAddedChar);

    private char getFirstChar(@NotNull char[] chars) {
        if (chars.length > 0) {
            return chars[0];
        } else {
            return '\0';
        }
    }
}
