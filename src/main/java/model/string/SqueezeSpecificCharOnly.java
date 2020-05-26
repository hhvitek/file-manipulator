package model.string;

/**
 * Squeezes only squeezable sequences of the one specific character
 *
 * For example.
 * Input: aaXbbBBBcCCC
 * squeeze: 'a'
 * Output: aXbbBBBcCCC
 */
public class SqueezeSpecificCharOnly extends SqueezeEverything {

    private char squeezeWhat;

    public SqueezeSpecificCharOnly(char squeezeWhat) {
        super();
        this.squeezeWhat = squeezeWhat;
    }

    @Override
    protected boolean thisCharIsRelevantForSqueezing(char c) {
        return c == squeezeWhat;
    }

}
