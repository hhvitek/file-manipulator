package app.utilities.string_filters.operations.whitelist;

import app.utilities.string_filters.FilterException;
import app.utilities.string_filters.operations.CharacterOperation;
import app.utilities.string_filters.ErrorCode;
import org.jetbrains.annotations.NotNull;

public class WhitelistCharacterFilter extends CharacterOperation {

    @Override
    public @NotNull String filter(@NotNull String input) {
        char[] inputAsArray = input.toCharArray();
        StringBuilder outputBuilder = new StringBuilder(inputAsArray.length);

        for(char character: inputAsArray) {
            if (filterCharacters.contains(character)) {
                outputBuilder.append(character);
            }
        }
        return outputBuilder.toString();
    }

    @Override
    protected String performOperation(@NotNull String input, char filterCharacter) {
        throw new FilterException(ErrorCode.UNSUPPORTED_OPERATION, "WhitelistCharacterFilter.performOperation()");
    }
}