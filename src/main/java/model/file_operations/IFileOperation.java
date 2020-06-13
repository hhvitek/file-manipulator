package model.file_operations;

import java.nio.file.Path;

public abstract class IFileOperation {

    protected FileOperation fileOperation = new FileOperationImpl();

    public abstract void performFileOperation(Path inputFile, Path outputFile) throws FileOperationException;
}
