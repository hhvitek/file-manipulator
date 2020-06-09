package model.file_operations;

import java.nio.file.Path;

public class DeleteFileOperation extends IFileOperation {

    @Override
    public void performFileOperation(Path inputFile, Path outputFile) throws FileOperationException {
        fileOperation.delete(inputFile);
    }
}
