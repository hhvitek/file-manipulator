package model.filters.operations;

import model.filters.Filter;
import model.filters.FilterException;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import static model.filters.ErrorCode.*;

public abstract class RegexOperation implements Filter {

    private static final Logger logger = LoggerFactory.getLogger(RegexOperation.class);

    protected String replaceWith;

    protected Pattern filteredPattern;

    protected RegexOperation() {
        replaceWith = "";
    }

    protected RegexOperation(@NotNull Pattern filteredPattern) throws FilterException {
        this();
        addNextFilter(filteredPattern);
    }

    public void addNextFilter(@NotNull Pattern additionalFilteredPattern) throws FilterException {
        if (filteredPattern == null) {
            filteredPattern = additionalFilteredPattern;
        } else {
            filteredPattern = mergeTwoPatternsUsingORPredicate(filteredPattern, additionalFilteredPattern);
        }
    }

    private Pattern mergeTwoPatternsUsingORPredicate(@NotNull Pattern firstPattern, @NotNull Pattern additionalPattern)
            throws FilterException {
        return compileStringRepresentationOfPattern(
                surroundPatternWithBrackets(firstPattern)
                        .pattern()
                        + "|" +
                        surroundPatternWithBrackets(additionalPattern)
                                .pattern()
        );
    }

    private Pattern compileStringRepresentationOfPattern(@NotNull String patternRepresentation) throws FilterException {
        try {
            return Pattern.compile(patternRepresentation);
        } catch (PatternSyntaxException ex) {
            logger.error("Illegal filter pattern syntax: {}", patternRepresentation);
            throw new FilterException(ILLEGAL_FILTER_PATTERN_SYNTAX, patternRepresentation);
        }
    }

    private Pattern surroundPatternWithBrackets(@NotNull Pattern input) throws FilterException {
        return compileStringRepresentationOfPattern("(" + input.pattern() + ")");
    }

    @Override
    public void addNextFilter(@NotNull String nextFilter) throws FilterException {
        addNextFilter(compileStringRepresentationOfPattern(nextFilter));
    }

    public void replaceWith(@NotNull String replaceWith) {
        this.replaceWith = replaceWith;
    }

    @Override
    public @NotNull String filter(@NotNull String input) {
        if (!hasAnyPattern()) {
            logger.warn("No regex filter has been set! No changes to input string done!");
            return input;
        }

        return performOperation(input, filteredPattern);
    }

    private boolean hasAnyPattern() {
        return (filteredPattern != null);
    }

    protected abstract String performOperation(@NotNull String input, @NotNull Pattern filteredPattern);
}
