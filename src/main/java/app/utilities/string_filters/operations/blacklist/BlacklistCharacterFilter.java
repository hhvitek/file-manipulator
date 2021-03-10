package app.utilities.string_filters.operations.blacklist;

import app.utilities.string_filters.operations.CharacterOperation;
import org.jetbrains.annotations.NotNull;

public class BlacklistCharacterFilter extends CharacterOperation {

    @Override
    protected String performOperation(@NotNull String input, char filterCharacter) {
        return stringAdditionalOperations.replaceWhatTo(
                input,
                String.valueOf(filterCharacter),
                replaceWith
        );
    }
}