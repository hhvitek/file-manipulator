package model.string;

import model.string.squeeze.SqueezeEverything;
import model.string.squeeze.SqueezeSpecificCharOnly;
import model.string.squeeze.SqueezeSpecificCharRegexOnly;
import model.string.squeeze.StringSqueeze;
import org.jetbrains.annotations.NotNull;

import java.util.regex.Pattern;

/**
 * Only Java SE API
 * 3rd party libraries for manipulating String NOT USED:
 *      - Apache Commons's StringUtils and RegExUtils
 *      - Google's Guava
 */
public final class StringUtilityOperations {

    // This is Utility class, only static methods
    private StringUtilityOperations() {
    }

    public static String replaceWhatTo(@NotNull String input, @NotNull String replaceWhat, @NotNull String replaceWith) {
        return input.replace(replaceWhat, replaceWith);
    }

    public static String replaceWhatRegexTo(@NotNull String input, @NotNull Pattern regexWhat, @NotNull String replaceWith) {
        return input.replaceAll(regexWhat.pattern(), replaceWith);
    }

    public static String squeezeWhatRegex(@NotNull String input, @NotNull Pattern regexWhat) {
        StringSqueeze squeeze = new SqueezeSpecificCharRegexOnly(regexWhat);
        return squeeze.squeeze(input);
    }

    public static String squeezeEverything(@NotNull String input) {
        StringSqueeze squeeze = new SqueezeEverything();
        return squeeze.squeeze(input);
    }

    public static String squeezeWhat(@NotNull String input, char squeezeWhat) {
        StringSqueeze squeeze = new SqueezeSpecificCharOnly(squeezeWhat);
        return squeeze.squeeze(input);
    }
}
