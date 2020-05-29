package model.filters.operations.whitelist;

import model.filters.operations.RegexOperation;
import org.jetbrains.annotations.NotNull;

import java.util.regex.Pattern;

public class WhitelistRegexFilter extends RegexOperation {
    @Override
    protected String performOperation(@NotNull String input, @NotNull Pattern filteredPattern) {
        return null;
    }
}
