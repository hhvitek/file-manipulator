package model.string_filters.operations.whitelist;

import model.string_filters.FilterException;
import model.string_filters.operations.CharacterOperation;
import org.jetbrains.annotations.NotNull;

import static model.string_filters.ErrorCode.*;

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
        throw new FilterException(UNSUPPORTED_OPERATION, "WhitelistCharacterFilter.performOperation()");
    }
}
