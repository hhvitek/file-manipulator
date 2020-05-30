package model.string_filters.operations.squeeze;

import model.string_filters.operations.RegexOperation;
import org.jetbrains.annotations.NotNull;

import java.util.regex.Pattern;

public class SqueezeRegexFilter extends RegexOperation {
    @Override
    protected String performOperation(@NotNull String input, @NotNull Pattern filterPattern) {
        return stringAdditionalOperations.squeezeWhatRegex(input, filterPattern);
    }

}
