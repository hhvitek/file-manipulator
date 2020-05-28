package model.filters.regex;

import model.filters.FilterAction;
import model.string.StringUtilityOperations;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.regex.Pattern;

import static model.filters.FilterAction.*;

public class RegexFilterRegex {

    private static final Logger logger = LoggerFactory.getLogger(RegexFilterRegex.class);

    private final Pattern regexPattern;
    private final String replaceWith;
    private final boolean shouldSqueeze;
    private final static char theMostUncommonChar = '\0';
    private FilterAction filterAction;

    public RegexFilterRegex(@NotNull Pattern regexPattern, @NotNull String replaceWith, boolean shouldSqueeze) {
        this.regexPattern = regexPattern;
        this.replaceWith = replaceWith;
        this.shouldSqueeze = shouldSqueeze;
        filterAction = determineFilterAction();
    }

    private FilterAction determineFilterAction() {
        if (!shouldSqueeze && replaceWith.isEmpty()) {
            return DELETE;
        }

        if (!shouldSqueeze && !replaceWith.isEmpty()) {
            return REPLACE_ONLY_WITH;
        }

        if (shouldSqueeze && replaceWith.isEmpty()) {
            return SQUEEZE_ONLY;
        }

        return SQUEEZE_AND_REPLACE_WITH;
    }

    public String filter(@NotNull String input) {
        switch (filterAction) {
            case DELETE:
            case REPLACE_ONLY_WITH: {
                return StringUtilityOperations.replaceWhatRegexTo(input, regexPattern, replaceWith);
            }

            case SQUEEZE_ONLY: {
                return StringUtilityOperations.squeezeWhatRegex(input, regexPattern);
            }

            default: /*SQUEEZE_AND_REPLACE_WITH*/ {
                String intermediateReplacedOutput = StringUtilityOperations.replaceWhatRegexTo(
                        input,
                        regexPattern,
                        String.valueOf(theMostUncommonChar)
                );
                String intermediateReplacedAndSqueezed =  StringUtilityOperations.squeezeWhat(
                        intermediateReplacedOutput,
                        theMostUncommonChar
                );
                return StringUtilityOperations.replaceWhatTo(
                        intermediateReplacedAndSqueezed,
                        String.valueOf(theMostUncommonChar),
                        replaceWith
                );
            }
        }
    }

}
