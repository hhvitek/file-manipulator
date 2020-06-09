package model.jobs.file_locators;

public class FileLocatorException extends RuntimeException {

    public FileLocatorException(String message) {
        super(message);
    }

    public FileLocatorException(Throwable throwable) {
        super(throwable);
    }
}
