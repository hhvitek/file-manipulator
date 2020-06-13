package model.string_filters.operations;

import model.string_filters.FilterException;
import org.jetbrains.annotations.NotNull;

import java.util.regex.Pattern;

public abstract class ModifiedRegexOperation extends RegexOperation {

    protected Pattern modifiedFilterPattern;

    @Override
    public void addFilter(@NotNull Pattern newFilter) throws FilterException {
        super.addFilter(newFilter);
        patternHasChanged();
    }

    protected void patternHasChanged() {
        modifiedFilterPattern = modifyPattern(filterPattern);
    }

    protected abstract Pattern modifyPattern(@NotNull Pattern filterPattern);
}
