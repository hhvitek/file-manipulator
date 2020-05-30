package model.filters.operations.blacklist;

import model.filters.operations.RegexOperation;
import model.string.StringUtilityOperations;
import org.jetbrains.annotations.NotNull;

import java.util.regex.Pattern;

public class BlacklistRegexFilter extends RegexOperation {

    @Override
    protected String performOperation(@NotNull String input, @NotNull Pattern filteredPattern) {
        return stringAdditionalOperations.replaceWhatRegexTo(input, filteredPattern, replaceWith);
    }
}
