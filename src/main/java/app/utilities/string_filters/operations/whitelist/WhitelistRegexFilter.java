package app.utilities.string_filters.operations.whitelist;

import app.utilities.string_filters.operations.ModifiedRegexOperation;
import app.utilities.string_filters.FilterException;
import org.jetbrains.annotations.NotNull;

import java.util.regex.Pattern;

public class WhitelistRegexFilter extends ModifiedRegexOperation {

    @Override
    protected String performOperation(@NotNull String input, @NotNull Pattern filterPattern) {
        return stringAdditionalOperations.replaceWhatRegexTo(input, modifiedFilterPattern, replaceWith);
    }

    @Override
    protected Pattern modifyPattern(@NotNull Pattern patternToBeNegated) throws FilterException {
        String negatePatternAsString = negatePattern(patternToBeNegated.pattern());
        return compileStringRepresentationOfPattern(negatePatternAsString);
    }

    private String negatePattern(@NotNull String patternToBeNegated) {
        return "[^" + patternToBeNegated + "]";
    }
}