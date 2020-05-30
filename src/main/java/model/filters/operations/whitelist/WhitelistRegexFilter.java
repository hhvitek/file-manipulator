package model.filters.operations.whitelist;

import model.filters.FilterException;
import model.filters.operations.RegexOperation;
import org.jetbrains.annotations.NotNull;

import java.util.regex.Pattern;

public class WhitelistRegexFilter extends RegexOperation {

    private Pattern negatedAllPatternsMergedUsingOR;

    @Override
    protected String performOperation(@NotNull String input, @NotNull Pattern filterPattern) {
        return stringAdditionalOperations.replaceWhatRegexTo(input, negatedAllPatternsMergedUsingOR, replaceWith);
    }

    @Override
    public void addFilter(@NotNull Pattern newFilter) throws FilterException {
        super.addFilter(newFilter);
        patternHasChanged();
    }

    private void patternHasChanged() {
        negatedAllPatternsMergedUsingOR = negatePattern(filterPattern);
    }

    private Pattern negatePattern(@NotNull Pattern patternToBeNegated) throws FilterException {
        String negatePatternAsString = negatePattern(patternToBeNegated.pattern());
        return compileStringRepresentationOfPattern(negatePatternAsString);
    }

    private String negatePattern(@NotNull String patternToBeNegated) {
        return "[^" + patternToBeNegated + "]";
    }
}
