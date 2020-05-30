package model.file_operations;

import java.io.IOException;
import java.nio.file.Path;

public interface FileOperation {
    void copy(Path what, Path to) throws IOException;
    void rename(Path what, String newName) throws IOException;
    void move(Path what, Path to) throws IOException;
    void delete(Path what) throws IOException;
}
