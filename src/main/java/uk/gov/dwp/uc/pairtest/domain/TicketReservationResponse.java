package uk.gov.dwp.uc.pairtest.domain;

public class TicketReservationResponse {

    private final int status;
    private final String message;

    public TicketReservationResponse(int status, String message) {
        this.status = status;
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
