package model.file_operations;

import java.nio.file.Path;

public class MoveFileOperation extends IFileOperation {

    @Override
    public void performFileOperation(Path inputFile, Path outputFile) throws FileOperationException {
        fileOperation.move(inputFile, outputFile);
    }
}
