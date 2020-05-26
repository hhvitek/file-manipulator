package model.string;

import org.jetbrains.annotations.NotNull;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Squeezes only squeezable sequences of all characters specified by regular expression.
 *
 * For example.
 * Input: aaXbbBBBcCCC
 * squeeze: [aB]
 * Output: aXbbBcCCC
 */
public class SqueezeSpecificCharRegexOnly extends SqueezeEverything {

    private Pattern squeezeRegexWhat;

    public SqueezeSpecificCharRegexOnly(@NotNull Pattern squeezeRegexWhat) {
        super();
        this.squeezeRegexWhat = squeezeRegexWhat;
    }

    @Override
    protected boolean thisCharIsRelevantForSqueezing(char c) {
        Matcher m = squeezeRegexWhat.matcher(String.valueOf(c));
        return m.matches();
    }


}
