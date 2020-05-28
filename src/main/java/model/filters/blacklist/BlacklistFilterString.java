package model.filters.blacklist;

import model.filters.FilterAction;
import model.string.StringUtilityOperations;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static model.filters.FilterAction.*;

public class BlacklistFilterString {

    private static final Logger logger = LoggerFactory.getLogger(BlacklistFilterString.class);

    private final List<String> blacklist;
    private final String replaceWith;
    private final boolean shouldSqueeze;
    private FilterAction filterAction;
    private final static char theMostUncommonChar = '\0';


    public BlacklistFilterString(@NotNull List<String> blacklist, @NotNull String replaceWith, boolean shouldSqueeze) {
        this.blacklist = blacklist;
        this.replaceWith = replaceWith;
        this.shouldSqueeze = shouldSqueeze;
        filterAction = determineFilterAction();
    }

    private FilterAction determineFilterAction() {
        if (!shouldSqueeze && replaceWith.isEmpty()) {
            return DELETE;
        }

        if (!shouldSqueeze && !replaceWith.isEmpty()) {
            return REPLACE_ONLY_WITH;
        }

        if (shouldSqueeze && replaceWith.isEmpty()) {
            return SQUEEZE_ONLY;
        }

        return SQUEEZE_AND_REPLACE_WITH;
    }

    public String filter(@NotNull String input) {
        switch (filterAction) {
            case DELETE:
            case REPLACE_ONLY_WITH: {
                String output = input;
                for(String blacklistedString: blacklist) {
                    output = StringUtilityOperations.replaceWhatTo(output, blacklistedString, replaceWith);
                }
                return output;
            }

            case SQUEEZE_ONLY: {
                String output = input;
                for (String blacklistedString : blacklist) {
                    output = StringUtilityOperations.replaceWhatTo(output, blacklistedString, String.valueOf(theMostUncommonChar));
                }
                return StringUtilityOperations.squeezeWhat(output, theMostUncommonChar);
            }

            default: /*SQUEEZE_AND_REPLACE_WITH*/ {
                String intermediateReplaced = input;
                for (String blacklistedString : blacklist) {
                    intermediateReplaced = StringUtilityOperations.replaceWhatTo(intermediateReplaced, blacklistedString, String.valueOf(theMostUncommonChar));
                }
                String  intermediateReplacedAndSqueezed = StringUtilityOperations.squeezeWhat(intermediateReplaced, theMostUncommonChar);
                return StringUtilityOperations.replaceWhatTo(
                        intermediateReplacedAndSqueezed,
                        String.valueOf(theMostUncommonChar),
                        replaceWith
                );
            }
        }
    }

}
