package model.file_operations;

import java.nio.file.Path;

public class RenameFileOperation extends IFileOperation {

    @Override
    public void performFileOperation(Path inputFile, Path outputFile) throws FileOperationException {
        String outputFileName = outputFile.getFileName().toString();
        fileOperation.rename(inputFile, outputFileName);
    }
}
