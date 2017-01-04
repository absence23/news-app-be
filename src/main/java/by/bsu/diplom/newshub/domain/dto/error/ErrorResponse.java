package by.bsu.diplom.newshub.domain.dto.error;

import java.util.List;

/**
 * Entity to deliver error information to the client
 * Has messageCode code that can be localized, general messageCode
 * and list of errors if exception need to be detailed
 */
public class ErrorResponse<T> {
    private String messageCode;
    private String generalMessage;
    private List<T> errors;

    public ErrorResponse() {
    }

    public ErrorResponse(String messageCode, String generalMessage) {
        this.messageCode = messageCode;
        this.generalMessage = generalMessage;
    }

    public ErrorResponse(String messageCode, String generalMessage, List<T> errors) {
        this.messageCode = messageCode;
        this.generalMessage = generalMessage;
        this.errors = errors;
    }

    public String getGeneralMessage() {
        return generalMessage;
    }

    public void setGeneralMessage(String generalMessage) {
        this.generalMessage = generalMessage;
    }

    public String getMessageCode() {
        return messageCode;
    }

    public void setMessageCode(String messageCode) {
        this.messageCode = messageCode;
    }

    public List<T> getErrors() {
        return errors;
    }

    public void setErrors(List<T> errors) {
        this.errors = errors;
    }
}