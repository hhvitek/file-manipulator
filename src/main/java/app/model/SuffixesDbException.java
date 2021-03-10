package app.model;

public class SuffixesDbException extends RuntimeException {

    public SuffixesDbException() {
        super();
    }

    public SuffixesDbException(String errorMessage) {
        super(errorMessage);
    }

    public SuffixesDbException(String errorMessage, Throwable throwable) {
        super(errorMessage, throwable);
    }
}