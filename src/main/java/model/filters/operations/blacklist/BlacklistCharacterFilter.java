package model.filters.operations.blacklist;

import model.filters.operations.CharacterOperation;
import model.string.StringUtilityOperations;
import org.jetbrains.annotations.NotNull;

public class BlacklistCharacterFilter extends CharacterOperation {

    @Override
    protected String performOperation(@NotNull String input, @NotNull char filteredCharacter) {
        return StringUtilityOperations.replaceWhatTo(input, String.valueOf(filteredCharacter), replaceWith);
    }
}
