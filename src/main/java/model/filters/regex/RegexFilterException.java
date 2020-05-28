package model.filters.regex;

import model.filters.ErrorCode;
import model.filters.FilterException;

public class RegexFilterException extends FilterException {
    public RegexFilterException() {
    }

    public RegexFilterException(String message) {
        super(message);
    }

    public RegexFilterException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }
}
