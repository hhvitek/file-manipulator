package model.filters;

import org.jetbrains.annotations.NotNull;

/**
 * Filters/deletes unwanted characters from an input string.
 * May optionally apply more filters one by one.
 * May optionally squeeze unwanted characters into another character instead of deleting them
 */
public interface Filter {
    @NotNull String filter(@NotNull String input);
    void addNextFilter(@NotNull String nextFilter);
}
