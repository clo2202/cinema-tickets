package uk.gov.dwp.uc.pairtest.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * Immutable Object
 */
public final class TicketTypeRequest {

    @Min(value = 1, message = "number of tickets cannot be less than 1")
    private final int noOfTickets;
    @NotNull(message = "ticket type cannot be null")
    private final Type type;

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
        ADULT(20), CHILD(10), INFANT(0);

        private int price;

        Type(int price) {
            this.price = price;
        }

        public int getPrice() {
            return price;
        }
    }

}
