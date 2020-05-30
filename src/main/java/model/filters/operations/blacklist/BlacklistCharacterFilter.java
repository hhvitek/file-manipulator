package model.filters.operations.blacklist;

import model.filters.operations.CharacterOperation;
import model.string.StringUtilityOperations;
import org.jetbrains.annotations.NotNull;

public class BlacklistCharacterFilter extends CharacterOperation {

    @Override
    protected String performOperation(@NotNull String input, char filteredCharacter) {
        return stringAdditionalOperations.replaceWhatTo(input, String.valueOf(filteredCharacter), replaceWith);
    }
}
