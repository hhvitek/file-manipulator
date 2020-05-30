package model.string_filters.operations.trim;

import model.string_filters.FilterException;
import model.string_filters.operations.ModifiedRegexOperation;
import org.jetbrains.annotations.NotNull;

import java.util.regex.Pattern;

public class TrimRegexFilter extends ModifiedRegexOperation {

    private final static Pattern DEFAULT_PATTERN = Pattern.compile("\\s+");

    public TrimRegexFilter() {
        addFilter(DEFAULT_PATTERN);
    }

    @Override
    protected Pattern modifyPattern(@NotNull Pattern filterPattern) throws FilterException {
        return compileStringRepresentationOfPattern(
                String.format("^%s|%s$", filterPattern, filterPattern)
        );
    }

    @Override
    protected String performOperation(@NotNull String input, @NotNull Pattern filterPattern) {
        if (isWhitespacePattern(filterPattern)) {
            return input.trim();
        } else {
            return performCustomTrim(input);
        }
    }

    private boolean isWhitespacePattern(@NotNull Pattern pattern) {
        return pattern == DEFAULT_PATTERN;
    }

    private String performCustomTrim(@NotNull String input) {
        return stringAdditionalOperations.replaceWhatRegexTo(input, modifiedFilterPattern, "");
    }


}
