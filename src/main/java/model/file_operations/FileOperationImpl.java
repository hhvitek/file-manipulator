package model.file_operations;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public class FileOperationImpl implements FileOperation {
    @Override
    public void copy(Path what, Path to) throws IOException {
        Files.copy(what, to, StandardCopyOption.COPY_ATTRIBUTES, StandardCopyOption.REPLACE_EXISTING);
    }

    @Override
    public void rename(Path what, String newName) throws IOException {
        Path newPath = what.resolveSibling(newName);
        move(what, newPath);
    }

    @Override
    public void move(Path what, Path to) throws IOException {
        Files.move(what, to, StandardCopyOption.COPY_ATTRIBUTES, StandardCopyOption.REPLACE_EXISTING);
    }

    @Override
    public void delete(Path what) throws IOException {
        Files.deleteIfExists(what);
    }
}
