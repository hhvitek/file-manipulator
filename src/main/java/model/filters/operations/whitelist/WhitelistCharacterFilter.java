package model.filters.operations.whitelist;

import model.filters.FilterException;
import model.filters.operations.CharacterOperation;
import org.jetbrains.annotations.NotNull;

import static model.filters.ErrorCode.*;

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
