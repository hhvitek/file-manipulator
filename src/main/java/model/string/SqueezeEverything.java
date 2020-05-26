package model.string;

/**
 * Squeezes every squeezable sequence of characters.
 *
 * Squeezable sequence of character is defined as a consecutive sequence of the same characters
 * => It's char operation (char a == char b) returns true
 *
 * For example.
 * Input: aaXbbBBBcCCC
 * Output: aXbBcC
 */
public class SqueezeEverything extends StringSqueeze {

    protected char currentChar;
    protected char lastAddedChar;

    @Override
    protected boolean shouldAppendChar(char currentChar, char lastAddedChar) {
        this.currentChar = currentChar;
        this.lastAddedChar = lastAddedChar;

        if (thoseAreConsecutiveSqueezableCharacters()) {
            return false;
        }
        return true;
    }

    protected boolean thoseAreConsecutiveSqueezableCharacters() {
        return thisCharIsRelevantForSqueezing(currentChar)
                && thisCharIsRelevantForSqueezing(lastAddedChar);
    }

    protected boolean thisCharIsRelevantForSqueezing(char c) {
        return c == lastAddedChar;
    }}
