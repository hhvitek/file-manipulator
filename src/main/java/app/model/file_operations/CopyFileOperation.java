package app.model.file_operations;

import java.nio.file.Path;

public class CopyFileOperation extends IFileOperation {

    @Override
    public void performFileOperation(Path inputFile, Path outputFile) throws FileOperationException {
        fileOperation.copy(inputFile, outputFile);
    }
}