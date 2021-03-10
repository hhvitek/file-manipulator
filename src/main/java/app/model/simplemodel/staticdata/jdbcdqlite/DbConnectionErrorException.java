package app.model.simplemodel.staticdata.jdbcdqlite;

import app.model.SuffixesDbException;

public class DbConnectionErrorException extends SuffixesDbException {

    public DbConnectionErrorException() {  }

    public DbConnectionErrorException(String message) {
        super(message);
    }

    public DbConnectionErrorException(String message, Throwable cause) {
        super(message, cause);
    }
}