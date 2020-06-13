package model.string_filters.operations.filename;

import model.string_filters.FilterException;
import model.string_filters.operations.Operation;
import model.string_filters.operations.chain_of_operations.ChainOfOperations;
import model.string_filters.operations.squeeze.SqueezeCharacterFilter;
import model.string_filters.operations.trim.TrimCharacterFilter;
import model.string_filters.operations.whitelist.WhitelistRegexFilter;
import org.jetbrains.annotations.NotNull;

import static model.string_filters.ErrorCode.UNSUPPORTED_OPERATION;

public class FileNameFilterOperation extends Operation {

    private ChainOfOperations operationsChain;

    public FileNameFilterOperation() {
        operationsChain = createFileNameOperationsChain();
    }

    @SuppressWarnings("HardcodedFileSeparator")
    private ChainOfOperations createFileNameOperationsChain() {
        operationsChain = new ChainOfOperations();

        WhitelistRegexFilter whitelistRegexFilter = new WhitelistRegexFilter();
        whitelistRegexFilter.addFilter("a-zA-Z0-9_\\-\\)\\(\\]\\[\\+");
        whitelistRegexFilter.replaceWith("_");
        operationsChain.addOperation(whitelistRegexFilter);

        SqueezeCharacterFilter squeezeCharacterFilter = new SqueezeCharacterFilter();
        squeezeCharacterFilter.addFilter('_');
        operationsChain.addOperation(squeezeCharacterFilter);

        TrimCharacterFilter trimCharacterFilter = new TrimCharacterFilter();
        trimCharacterFilter.addFilter('_');
        operationsChain.addOperation(trimCharacterFilter);

        return operationsChain;
    }

    @Override
    public @NotNull String filter(@NotNull String input) {
        return operationsChain.filter(input);
    }

    @Override
    public void addFilter(@NotNull String nextFilter) {
        throw new FilterException(UNSUPPORTED_OPERATION, "FileNameFilterOperation.addFilter()");
    }
}
