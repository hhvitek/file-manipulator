package model.filters.regex;

import model.filters.Filter;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class RegexFilter implements Filter {

    private static final Logger logger = LoggerFactory.getLogger(RegexFilter.class);

    private boolean shouldSqueeze;
    private String replaceWith;

    private Pattern pattern;

    public RegexFilter() {
        shouldSqueeze = false;
        replaceWith = "";
    }

    public RegexFilter(@NotNull String newFilter) throws RegexFilterException {
        this();
        addNextFilter(newFilter);
    }

    @Override
    public String filter(@NotNull String input) {
        if (!hasAnyFilters()) {
            logger.warn("No regex filter has been set! No changes to input string done!");
            return input;
        }

        RegexFilterRegex regexFilterRegex = new RegexFilterRegex(pattern, replaceWith, shouldSqueeze);
        String output = regexFilterRegex.filter(input);
        return output;
    }

    private boolean hasAnyFilters() {
        return (pattern != null);
    }

    @Override
    public Filter addNextFilter(@NotNull String nextFilter) throws PatternSyntaxException {
        if (pattern == null) {
            pattern = Pattern.compile(
                    surroundWithBrackets(nextFilter)
            );
        } else {
            pattern = mergePatternAndStringUsingOR(pattern, nextFilter);
        }
        return this;
    }

    private String surroundWithBrackets(@NotNull String input) {
        return "(" + input + ")";
    }

    private Pattern mergePatternAndStringUsingOR(@NotNull Pattern pattern, @NotNull String newPattern) throws PatternSyntaxException {
        return Pattern.compile(
                pattern.pattern()
              + "|"
              + surroundWithBrackets(newPattern)
        );
    }


    @Override
    public void optionallyReplaceFilteredStringsWith(@NotNull String replaceWith) {
        this.replaceWith = replaceWith;
    }

    @Override
    public void optionallyShouldSqueezeConsecutiveFilteredStrings(boolean shouldSqueeze) {
        this.shouldSqueeze = shouldSqueeze;
    }
}
