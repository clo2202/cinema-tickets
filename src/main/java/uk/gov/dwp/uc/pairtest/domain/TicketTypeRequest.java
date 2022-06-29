package uk.gov.dwp.uc.pairtest.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * Immutable Object
 */
public class TicketTypeRequest {

    @Min(value = 1, message = "number of tickets cannot be less than 1")
    private int noOfTickets;
    @NotNull(message = "ticket type cannot be null")
    private Type type;

    public TicketTypeRequest(@JsonProperty("type") Type type,
                             @JsonProperty("noOfTickets") int noOfTickets) {
        this.type = type;
        this.noOfTickets = noOfTickets;
    }

    public int getNoOfTickets() {
        return noOfTickets;
    }

    public Type getTicketType() {
        return type;
    }

    public enum Type {
        ADULT, CHILD , INFANT
    }

}
