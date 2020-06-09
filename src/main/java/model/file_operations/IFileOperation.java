package model.file_operations;

import java.nio.file.Path;

abstract public class IFileOperation {

    protected FileOperation fileOperation = new FileOperationImpl();

    abstract public void performFileOperation(Path inputFile, Path outputFile) throws FileOperationException;
}
