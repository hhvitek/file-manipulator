package model.filters;

import org.jetbrains.annotations.NotNull;

/**
 * Filters/deletes unwanted characters from an input string.
 * May optionally apply more filters one by one.
 * May optionally squeeze unwanted characters into another character instead of deleting them
 */
public interface Filter {
    String filter(@NotNull String input);

    /**
     * Filters are applied one by one. In order of addition.
     */
    Filter addNextFilter(@NotNull String nextFilter);

    /**
     * Replace instead of deletion.
     */
    void optionallyReplaceFilteredStringsWith(@NotNull String replaceWith);

    /**
     * Behaves same as "tr -s" command. Squeezes filtered string into only one string.
     */
    void optionallyShouldSqueezeConsecutiveFilteredStrings(boolean shouldSqueeze);
}
