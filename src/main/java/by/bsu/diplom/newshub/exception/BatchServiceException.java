package by.bsu.diplom.newshub.exception;

public class BatchServiceException extends RuntimeException {
    public BatchServiceException(String message) {
        super(message);
    }

    public BatchServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
