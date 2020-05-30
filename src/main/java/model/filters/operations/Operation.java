package model.filters.operations;

import model.string.CustomStringAdditionalOperationsImpl;
import model.string.StringAdditionalOperations;
import org.jetbrains.annotations.NotNull;

abstract public class Operation {
    protected StringAdditionalOperations stringAdditionalOperations;

    protected Operation() {
        stringAdditionalOperations = new CustomStringAdditionalOperationsImpl();
    }

    abstract public @NotNull String filter(@NotNull String input);

    abstract public void addFilter(@NotNull String nextFilter);
}
