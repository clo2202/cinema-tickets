package uk.gov.dwp.uc.pairtest;

import org.springframework.stereotype.Service;
import uk.gov.dwp.uc.pairtest.domain.TicketTypeRequest;
import uk.gov.dwp.uc.pairtest.exception.InvalidPurchaseException;

@Service
public class TicketServiceImpl implements TicketService {

    /**
     * Should only have private methods other than the one below.
     */
    @Override
    public void purchaseTickets(Long accountId, TicketTypeRequest... ticketTypeRequests) throws InvalidPurchaseException {

        // validate ticketTypeRequest (not more than 20 tickets and contains an adult)
        // calculate price
        // calculate no. of tickets
        // call TicketPaymentService
        // call SeatReservationService

    }
}
