package by.bsu.diplom.newshub.exception;


import org.springframework.validation.BindingResult;

public class ValidationException extends RuntimeException {
    private static final String START_MESSAGE = "Entity is not correct: \n";
    private BindingResult bindingResult;

    public ValidationException(BindingResult bindingResult) {
        this.bindingResult = bindingResult;
    }

    public BindingResult getBindingResult() {
        return bindingResult;
    }

    public String getMessage() {
        StringBuilder stringBuilder = new StringBuilder(START_MESSAGE);
        bindingResult.getFieldErrors().forEach(error -> stringBuilder.append(error.getDefaultMessage()).append(";\n"));
        return stringBuilder.toString();
    }
}
