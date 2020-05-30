package model.string_filters.operations.blacklist;

import model.string_filters.operations.RegexOperation;
import org.jetbrains.annotations.NotNull;

import java.util.regex.Pattern;

public class BlacklistRegexFilter extends RegexOperation {

    @Override
    protected String performOperation(@NotNull String input, @NotNull Pattern filterPattern) {
        return stringAdditionalOperations.replaceWhatRegexTo(input, filterPattern, replaceWith);
    }
}
