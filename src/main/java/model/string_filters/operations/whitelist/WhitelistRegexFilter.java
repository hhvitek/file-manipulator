package model.string_filters.operations.whitelist;

import model.string_filters.FilterException;
import model.string_filters.operations.ModifiedRegexOperation;
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
