package com.epam.esm.task4.parser.exception;


public class ParsingException extends RuntimeException {
    public ParsingException(String message) {
        super(message);
    }

    public ParsingException(String message, Throwable cause) {
        super(message, cause);
    }
}