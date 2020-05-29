package model.filters.operations.squeeze;

import model.filters.operations.CharacterOperation;
import model.string.StringUtilityOperations;
import org.jetbrains.annotations.NotNull;

public class SqueezeCharacterFilter extends CharacterOperation {

    @Override
    protected String performOperation(@NotNull String input, char filteredCharacter) {
        return StringUtilityOperations.squeezeWhat(input, filteredCharacter);
    }
}
