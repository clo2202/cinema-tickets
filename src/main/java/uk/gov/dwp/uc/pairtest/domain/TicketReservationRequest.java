package uk.gov.dwp.uc.pairtest.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

public class TicketReservationRequest {

    @NotNull(message = "account id cannot be null")
    @Min(value = 1L, message = "account id cannot be less than 0")
    private Long accountId;

    @NotEmpty(message = "ticket type list cannot be empty")
    @Valid
    private List<TicketTypeRequest> ticketTypeRequests;

    public TicketReservationRequest(@JsonProperty("accountId") Long accountId,
                                    @JsonProperty("ticketTypeRequests") List<TicketTypeRequest> ticketTypeRequests) {
        this.accountId = accountId;
        this.ticketTypeRequests = ticketTypeRequests;
    }

    public Long getAccountId() {
        return accountId;
    }

    public List<TicketTypeRequest> getTicketTypeRequests() {
        return ticketTypeRequests;
    }
}
