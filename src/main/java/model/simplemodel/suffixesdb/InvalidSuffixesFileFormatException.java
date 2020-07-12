package model.simplemodel.suffixesdb;

import model.SuffixesDbException;

public class InvalidSuffixesFileFormatException extends SuffixesDbException {

    public InvalidSuffixesFileFormatException(String errorMessage) {
        super(errorMessage);
    }
}
