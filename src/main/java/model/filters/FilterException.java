package model.filters;

public class FilterException extends RuntimeException {
        protected ErrorCode errorCode = ErrorCode.OK;

        public FilterException() {
        }

        public FilterException(String message) {
            super(message);
        }

        public FilterException(ErrorCode errorCode) {
            this.errorCode = errorCode;
        }

        public ErrorCode getErrorCode() {
            return errorCode;
        }

        public void setErrorCode(ErrorCode errorCode) {
            this.errorCode = errorCode;
        }

        public String errorMessage() {
            switch (errorCode) {
                case OK:
                    return "TILT: Should not get here.";
                case UNEXPECTED_ARGUMENT:
                    return String.format("Argument -%c unexpected.");
            }
            return "";
        }
}
