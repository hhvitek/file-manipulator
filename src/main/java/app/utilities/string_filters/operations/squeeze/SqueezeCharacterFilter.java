package app.utilities.string_filters.operations.squeeze;

import app.utilities.string_filters.operations.CharacterOperation;
import org.jetbrains.annotations.NotNull;

public class SqueezeCharacterFilter extends CharacterOperation {

    @Override
    protected String performOperation(@NotNull String input, char filterCharacter) {
        return stringAdditionalOperations.squeezeWhat(input, filterCharacter);
    }
}