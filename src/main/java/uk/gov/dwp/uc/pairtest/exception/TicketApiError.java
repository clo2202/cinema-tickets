package uk.gov.dwp.uc.pairtest.exception;

import java.util.List;

public class TicketApiError {

    private final int status;
    private final String message;
    private final List<String> errors;

    public TicketApiError(int status, String message, List<String> errors) {
        this.status = status;
        this.message = message;
        this.errors = errors;
    }

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public List<String> getErrors() {
        return errors;
    }
}
