package model.filters.operations;

import model.filters.Filter;
import model.filters.FilterException;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import static model.filters.ErrorCode.ILLEGAL_FILTER_PATTERN_SYNTAX;

public abstract class RegexOperation implements Filter {

    private static final Logger logger = LoggerFactory.getLogger(RegexOperation.class);

    protected String replaceWith;

    protected List<Pattern> filteredPatterns;

    protected RegexOperation() {
        replaceWith = "";
        filteredPatterns = new ArrayList<>();
    }

    protected RegexOperation(@NotNull Pattern filteredPattern) throws FilterException {
        this();
        addNextFilter(filteredPattern);
    }

    public void addNextFilter(@NotNull Pattern additionalFilteredPattern) throws FilterException {
        filteredPatterns.add(additionalFilteredPattern);
    }

    @Override
    public void addNextFilter(@NotNull String nextFilter) throws FilterException {
        addNextFilter(compileStringRepresentationOfPattern(nextFilter));
    }

    protected Pattern compileStringRepresentationOfPattern(@NotNull String patternRepresentation) throws FilterException {
        try {
            return Pattern.compile(patternRepresentation);
        } catch (PatternSyntaxException ex) {
            logger.error("Illegal filter pattern syntax: {}", patternRepresentation);
            throw new FilterException(ILLEGAL_FILTER_PATTERN_SYNTAX, patternRepresentation);
        }
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

        String output = input;
        for(Pattern filteredPattern: filteredPatterns) {
            output = performOperation(output, filteredPattern);
        }
        return output;
    }

    protected boolean hasAnyPattern() {
        return (!filteredPatterns.isEmpty());
    }

    protected abstract String performOperation(@NotNull String input, @NotNull Pattern filteredPattern);
}
