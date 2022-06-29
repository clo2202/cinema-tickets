package uk.gov.dwp.uc.pairtest.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import uk.gov.dwp.uc.pairtest.domain.TicketTypeRequest;
import uk.gov.dwp.uc.pairtest.exception.InvalidPurchaseException;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TicketServiceImpl implements TicketService {

    private int maxNoOfTicketsAllowed = 20;

    /**
     * Should only have private methods other than the one below.
     */
    @Override
    public void purchaseTickets(Long accountId, TicketTypeRequest... ticketTypeRequests) throws InvalidPurchaseException {
        validateTicketPurchase(ticketTypeRequests);
    }

    private void validateTicketPurchase(TicketTypeRequest[] ticketTypeRequests) {
        List<TicketTypeRequest> totalAdults = Arrays.stream(ticketTypeRequests)
                .filter(request -> request.getTicketType() ==  TicketTypeRequest.Type.ADULT)
                .collect(Collectors.toList());

        int totalTickets = Arrays.stream(ticketTypeRequests).mapToInt(request -> request.getNoOfTickets()).sum();

        if (totalAdults.size() < 1) {
            throw new InvalidPurchaseException("Purchase must include at least 1 adult");
        }

        if (totalTickets > maxNoOfTicketsAllowed) {
            throw new InvalidPurchaseException("Exceeded maximum number of tickets");
        }
    }
}
