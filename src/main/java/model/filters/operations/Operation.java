package model.filters.operations;

import model.string.CustomStringAdditionalOperationsImpl;
import model.string.StringAdditionalOperations;

public abstract class Operation {
    protected StringAdditionalOperations stringAdditionalOperations;

    protected Operation() {
        stringAdditionalOperations = new CustomStringAdditionalOperationsImpl();
    }
}
