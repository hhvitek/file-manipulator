package app.utilities.string_filters.operations.filename;

import app.utilities.string_filters.FilterException;
import app.utilities.string_filters.operations.Operation;
import app.utilities.string_filters.operations.chain_of_operations.ChainOfOperations;
import app.utilities.string_filters.operations.squeeze.SqueezeCharacterFilter;
import app.utilities.string_filters.operations.trim.TrimCharacterFilter;
import app.utilities.string_filters.operations.whitelist.WhitelistRegexFilter;
import app.utilities.string_filters.ErrorCode;
import org.jetbrains.annotations.NotNull;

public class FileNameFilterOperation extends Operation {

    private ChainOfOperations operationsChain;

    public FileNameFilterOperation() {
        operationsChain = createFileNameOperationsChain();
    }

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
        throw new FilterException(ErrorCode.UNSUPPORTED_OPERATION, "FileNameFilterOperation.addFilter()");
    }
}