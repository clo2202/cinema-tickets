package uk.gov.dwp.uc.pairtest.service.calculator;

import org.springframework.stereotype.Service;
import uk.gov.dwp.uc.pairtest.domain.TicketTypeRequest;

import java.util.Arrays;

@Service
public class TicketReservationCalculatorImpl implements TicketReservationCalculator {

    @Override
    public int calculateTotalPrice(TicketTypeRequest... ticketTypeRequests) {
        return Arrays.stream(ticketTypeRequests)
                .mapToInt(request -> request.getTicketType().getPrice() * request.getNoOfTickets())
                .sum();
    }
}
