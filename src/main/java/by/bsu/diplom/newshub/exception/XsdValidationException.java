package by.bsu.diplom.newshub.exception;


public class XsdValidationException extends RuntimeException {
    public XsdValidationException(String message) {
        super(message);
    }

    public XsdValidationException(String message, Throwable cause) {
        super(message, cause);
    }
}
