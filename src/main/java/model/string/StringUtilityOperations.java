package model.string;

import org.jetbrains.annotations.NotNull;

import java.util.regex.Pattern;

/**
 * Only Java SE API
 * 3rd party libraries for manipulating String NOT USED:
 *      - Apache Commons's StringUtils and RegExUtils
 *      - Google's Guava
 */
public class StringUtilityOperations {

    public static String replaceWhatTo(@NotNull String input, @NotNull String replaceWhat, @NotNull String replaceTo) {
        return input.replace(replaceWhat, replaceTo);
    }

    public static String replaceWhatRegexTo(@NotNull String input, @NotNull Pattern regexWhat, @NotNull String replaceTo) {
        return input.replaceAll(regexWhat.pattern(), replaceTo);
    }

    public static String squeezeWhatRegex(@NotNull String input, @NotNull Pattern regexWhat) {
        StringSqueeze squeeze = new SqueezeSpecificCharRegexOnly(regexWhat);
        return squeeze.squeeze(input);
    }

    public static String squeezeEverything(@NotNull String input) {
        StringSqueeze squeeze = new SqueezeEverything();
        return squeeze.squeeze(input);
    }

    public static String squeezeWhat(@NotNull String input, @NotNull char squeezeWhat) {
        StringSqueeze squeeze = new SqueezeSpecificCharOnly(squeezeWhat);
        return squeeze.squeeze(input);
    }
}
