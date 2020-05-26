package model.filters;

import org.jetbrains.annotations.NotNull;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class RegexFilterRegex {

    private final Pattern pattern;

    public RegexFilterRegex(@NotNull String regexPattern) throws PatternSyntaxException {
        this.pattern = Pattern.compile(regexPattern);
    }

    public String filterDelete(@NotNull String input) {
        return filterReplace(input, "");
    }

    public String filterReplace(@NotNull String input, @NotNull String replaceWith) {
        Matcher m = pattern.matcher(input);
        return m.replaceAll(replaceWith);
    }
}
