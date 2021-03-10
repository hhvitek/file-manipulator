package app.utilities.string_filters.operations.chain_of_operations;

import app.utilities.string_filters.operations.Operation;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class ChainOfOperations {

    private List<Operation> operations;

    public ChainOfOperations() {
        operations = new ArrayList<>();
    }

    public void addOperation(@NotNull Operation operation) {
        operations.add(operation);
    }

    public String filter(@NotNull String input) {
        String output = input;
        for(Operation operation: operations) {
            output = operation.filter(output);
        }
        return output;
    }
}