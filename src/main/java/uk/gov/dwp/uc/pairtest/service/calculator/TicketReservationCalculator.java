package uk.gov.dwp.uc.pairtest.service.calculator;

import uk.gov.dwp.uc.pairtest.domain.TicketTypeRequest;

public interface TicketReservationCalculator {

    int calculateTotalPrice(TicketTypeRequest... ticketTypeRequests);
    int calculateTotalSeats(TicketTypeRequest... ticketTypeRequests);
}
