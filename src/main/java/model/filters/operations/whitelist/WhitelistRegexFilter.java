package model.filters.operations.whitelist;

import model.filters.FilterException;
import model.filters.operations.RegexOperation;
import org.jetbrains.annotations.NotNull;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static model.filters.ErrorCode.UNSUPPORTED_OPERATION;

public class WhitelistRegexFilter extends RegexOperation {

    private char[] inputAsArray;
    private char[] tmpOutput;

    @Override
    public @NotNull String filter(@NotNull String input) {
        inputAsArray = input.toCharArray();
        tmpOutput = input.toCharArray();

        for (Pattern pattern: filteredPatterns) {
            Matcher matcher = pattern.matcher(String.valueOf(tmpOutput));
            while (matcher.find()) {
                String matched = matcher.group();
            }
        }

    }



    @Override
    protected String performOperation(@NotNull String input, @NotNull Pattern filteredPattern) {

        throw new FilterException(UNSUPPORTED_OPERATION, "WhitelistCharacterFilter.performOperation()");
    }
}
