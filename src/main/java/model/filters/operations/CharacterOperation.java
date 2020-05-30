package model.filters.operations;

import model.filters.Filter;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public abstract class CharacterOperation extends Operation implements Filter {

    protected List<Character> filteredCharacters;
    protected String replaceWith;

    protected CharacterOperation() {
        filteredCharacters = new ArrayList<>();
        replaceWith = "";
    }

    @Override
    public @NotNull String filter(@NotNull String input) {
        String output = input;
        for (char filteredCharacter : filteredCharacters) {
            output = performOperation(output, filteredCharacter);
        }
        return output;
    }

    public void replaceWith(@NotNull String replaceWith) {
        this.replaceWith = replaceWith;
    }

    public void addNextFilter(char filteredCharacter) {
        filteredCharacters.add(filteredCharacter);
    }

    @Override
    public void addNextFilter(@NotNull String filteredString) {
        char[] filteredCharacters = filteredString.toCharArray();
        for (char filteredCharacter : filteredCharacters) {
            addNextFilter(filteredCharacter);
        }
    }

    protected abstract String performOperation(@NotNull String input, char filteredCharacter);
}
