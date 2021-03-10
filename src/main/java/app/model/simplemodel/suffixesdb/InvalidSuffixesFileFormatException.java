package app.model.simplemodel.suffixesdb;

import app.model.SuffixesDbException;

public class InvalidSuffixesFileFormatException extends SuffixesDbException {

    public InvalidSuffixesFileFormatException(String errorMessage) {
        super(errorMessage);
    }
}