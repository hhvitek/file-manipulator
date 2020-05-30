package model.string;

import org.jetbrains.annotations.NotNull;

import java.util.regex.Pattern;

public class CustomStringAdditionalOperationsImpl implements StringAdditionalOperations {
    @Override
    public String replaceWhatTo(@NotNull String input, @NotNull String replaceWhat, @NotNull String replaceWith) {
        return StringUtilityOperations.replaceWhatTo(input, replaceWhat, replaceWith);
    }

    @Override
    public String replaceWhatRegexTo(@NotNull String input, @NotNull Pattern regexWhat, @NotNull String replaceWith) {
        return StringUtilityOperations.replaceWhatRegexTo(input, regexWhat, replaceWith);
    }

    @Override
    public String squeezeWhatRegex(@NotNull String input, @NotNull Pattern regexWhat) {
        return StringUtilityOperations.squeezeWhatRegex(input, regexWhat);
    }

    @Override
    public String squeezeEverything(@NotNull String input) {
        return StringUtilityOperations.squeezeEverything(input);
    }

    @Override
    public String squeezeWhat(@NotNull String input, char squeezeWhat) {
        return StringUtilityOperations.squeezeWhat(input, squeezeWhat);
    }
}
