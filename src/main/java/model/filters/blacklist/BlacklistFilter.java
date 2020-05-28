package model.filters.blacklist;

import model.filters.Filter;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class BlacklistFilter implements Filter {

    private static final Logger logger = LoggerFactory.getLogger(BlacklistFilter.class);

    private boolean shouldSqueeze;
    private String replaceWith;

    private List<String> blacklist;

    public BlacklistFilter() {
        blacklist = new ArrayList<>();
        shouldSqueeze = false;
        replaceWith = "";
    }

    public BlacklistFilter(@NotNull String newFilter) {
        this();
        addNextFilter(newFilter);
    }

    @Override
    public String filter(@NotNull String input) {
        if (!hasAnyFilters()) {
            logger.warn("No regex filter has been set! No changes to input string done!");
            return input;
        }

        BlacklistFilterString blacklistFilterString = new BlacklistFilterString(blacklist, replaceWith, shouldSqueeze);
        String output = blacklistFilterString.filter(input);
        return output;
    }

    @Override
    public Filter addNextFilter(@NotNull String nextFilter) {
        blacklist.add(nextFilter);
        return this;
    }

    private boolean hasAnyFilters() {
        return (blacklist != null && !blacklist.isEmpty());
    }

    @Override
    public void optionallyReplaceFilteredStringsWith(@NotNull String replaceWith) {
        this.replaceWith = replaceWith;
    }

    @Override
    public void optionallyShouldSqueezeConsecutiveFilteredStrings(boolean shouldSqueeze) {
        this.shouldSqueeze = shouldSqueeze;
    }
}
