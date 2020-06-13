package model.string_filters.operations;

import model.string_operations.CustomStringAdditionalOperationsImpl;
import model.string_operations.StringAdditionalOperations;
import org.jetbrains.annotations.NotNull;

public abstract class Operation {
    protected StringAdditionalOperations stringAdditionalOperations;

    protected Operation() {
        stringAdditionalOperations = new CustomStringAdditionalOperationsImpl();
    }

    public abstract @NotNull String filter(@NotNull String input);

    public abstract void addFilter(@NotNull String nextFilter);
}
