package model.file_operations;

public class FileOperationException extends Exception {

    private FileOperationErrorsEnum fileOperationErrorsEnum;

    public FileOperationException(FileOperationErrorsEnum error, String message) {
        super(message);
        fileOperationErrorsEnum = error;
    }


    @Override
    public String toString() {
        return fileOperationErrorsEnum + getLocalizedMessage();
    }
}
