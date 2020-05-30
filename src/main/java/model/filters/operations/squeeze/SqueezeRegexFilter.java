package model.filters.operations.squeeze;

import model.filters.operations.RegexOperation;
import model.string.StringUtilityOperations;
import org.jetbrains.annotations.NotNull;

import java.util.regex.Pattern;

public class SqueezeRegexFilter extends RegexOperation {
    @Override
    protected String performOperation(@NotNull String input, @NotNull Pattern filteredPattern) {
        return stringAdditionalOperations.squeezeWhatRegex(input, filteredPattern);
    }

}
