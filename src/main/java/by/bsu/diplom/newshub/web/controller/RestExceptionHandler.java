package by.bsu.diplom.newshub.web.controller;

import by.bsu.diplom.newshub.domain.dto.error.ErrorResponse;
import by.bsu.diplom.newshub.domain.dto.error.ValidationError;
import by.bsu.diplom.newshub.exception.EntityAlreadyExistsException;
import by.bsu.diplom.newshub.exception.EntityNotFoundException;
import by.bsu.diplom.newshub.web.util.ErrorMessageCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.persistence.EntityExistsException;
import javax.persistence.PersistenceException;
import javax.validation.ValidationException;
import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class RestExceptionHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(RestExceptionHandler.class);
    private static final String INTERNAL_ERROR_MESSAGE = "Internal error";
    private static final String INVALID_BODY_MESSAGE = "Invalid body of request";
    private static final String VALIDATION_ERROR_MESSAGE = "Entity validation error";
    private static final String WRONG_ARGUMENT_MESSAGE = "Wrong request argument";
    private static final String ALREADY_EXISTS_MESSAGE = "Such entity already exists";

    @ExceptionHandler({PersistenceException.class, DataAccessException.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse processServiceException(Exception ex) {
        LOGGER.error("Internal error", ex);
        return new ErrorResponse(ErrorMessageCode.INTERNAL, INTERNAL_ERROR_MESSAGE);
    }

    @ExceptionHandler({EntityNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse processNotFoundException(EntityNotFoundException ex) {
        LOGGER.error(ex.getMessage());
        return new ErrorResponse(ErrorMessageCode.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler({ValidationException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse processValidationException(ValidationException ex) {
        LOGGER.error(ex.getMessage());
        return new ErrorResponse(ErrorMessageCode.INVALID_BODY, INVALID_BODY_MESSAGE);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse processArgumentValidationError(MethodArgumentNotValidException ex) {
        LOGGER.error(ex.getMessage());
        BindingResult result = ex.getBindingResult();
        List<FieldError> fieldErrors = result.getFieldErrors();
        List<ValidationError> validationErrors = new ArrayList<>();
        fieldErrors.forEach(error -> validationErrors.add(new ValidationError(error.getField(), error.getDefaultMessage())));
        return new ErrorResponse<>(ErrorMessageCode.NOT_VALID, VALIDATION_ERROR_MESSAGE, validationErrors);
    }

    @ExceptionHandler({MethodArgumentTypeMismatchException.class, IllegalArgumentException.class, HttpMessageNotReadableException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse processArgumentTypeValidationError(Exception ex) {
        LOGGER.error(ex.getMessage());
        return new ErrorResponse(ErrorMessageCode.ARGUMENT_MISMATCH, WRONG_ARGUMENT_MESSAGE);
    }

    @ExceptionHandler({EntityAlreadyExistsException.class, EntityExistsException.class, DataIntegrityViolationException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse processEntityAlreadyExistsError() {
        return new ErrorResponse(ErrorMessageCode.ALREADY_EXISTS, ALREADY_EXISTS_MESSAGE);
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse processMediaTypeNotSupported(HttpMediaTypeNotSupportedException ex) {
        LOGGER.error(ex.getMessage());
        return new ErrorResponse(ErrorMessageCode.HEADERS_NOT_ALLOWED, "Media type not supported");
    }

    @ExceptionHandler(HttpMediaTypeNotAcceptableException.class)
    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    public void process(HttpMediaTypeNotAcceptableException ex) {
        LOGGER.error("Not acceptable headers", ex);
    }
}
