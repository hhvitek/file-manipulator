package model.file_operations;

import java.nio.file.Path;

public class RenameFileOperation extends IFileOperation {

    @Override
    public void performFileOperation(Path inputFile, Path outputFile) throws FileOperationException {
        fileOperation.rename(inputFile, outputFile.getFileName().toString());
    }
}
