package model.filters;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.PatternSyntaxException;

public class RegexFilter implements Filter {

    private String squeezeInto;
    private List<RegexFilterRegex> regexFilters;

    public RegexFilter() {
        regexFilters = new ArrayList<>();
    }

    @Override
    public String filter(@NotNull String input) throws PatternSyntaxException {
        String output = input;
        for(RegexFilterRegex regexFilter: regexFilters) {
            output = regexFilter.filterReplace(output, squeezeInto);
        }
        return null;

    }

    @Override
    public void addNextFilter(@NotNull String nextFilter) {
        RegexFilterRegex regex = new RegexFilterRegex(nextFilter);
        regexFilters.add(regex);
    }

    @Override
    public void optionallySqueezeFilteredStringsInto(@NotNull String squeezeInto) {
        this.squeezeInto = squeezeInto;
    }
}
