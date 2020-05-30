package model.filters.operations.whitelist;

import model.filters.FilterException;
import model.filters.operations.RegexOperation;
import model.string.StringUtilityOperations;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class WhitelistRegexFilter extends RegexOperation {

    private Pattern negatedAllPatternsMergedUsingOR;

    @Override
    public @NotNull String filter(@NotNull String input) {
        if (!hasAnyPattern()) {
            return input;
        }

        return performOperation(input, negatedAllPatternsMergedUsingOR);
    }

    @Override
    protected String performOperation(@NotNull String input, @NotNull Pattern filteredPattern) {
        return stringAdditionalOperations.replaceWhatRegexTo(input, filteredPattern, replaceWith);
    }

    @Override
    public void addNextFilter(@NotNull Pattern additionalFilteredPattern) throws FilterException {
        super.addNextFilter(additionalFilteredPattern);
        patternHasChanged();
    }

    private void patternHasChanged() {
        Pattern allPatternsMergedUsingOR = mergeAllPatternsUsingORPredicate(filteredPatterns);
        negatedAllPatternsMergedUsingOR = negatePattern(allPatternsMergedUsingOR);
    }

    private Pattern mergeAllPatternsUsingORPredicate(@NotNull List<Pattern> patterns)
            throws FilterException {

        String patternsJoinedAsString = patterns.stream()
                .map(pattern -> surroundPatternWithBrackets(pattern.pattern()))
                .collect(Collectors.joining("|"));

        return compileStringRepresentationOfPattern(patternsJoinedAsString);
    }

    private String surroundPatternWithBrackets(@NotNull String input) {
        return "(" + input + ")";
    }

    private Pattern negatePattern(@NotNull Pattern patternToBeNegated) throws FilterException {
        String patternAsString = patternToBeNegated.pattern();
        String negatedPatternAsString = negatePattern(patternAsString);
        return compileStringRepresentationOfPattern(negatedPatternAsString);
    }

    private String negatePattern(@NotNull String patternToBeNegated) {
        return "[^" + patternToBeNegated + "]";
    }
}
