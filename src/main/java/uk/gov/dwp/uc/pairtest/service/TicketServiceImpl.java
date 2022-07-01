package uk.gov.dwp.uc.pairtest.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import thirdparty.paymentgateway.TicketPaymentService;
import thirdparty.seatbooking.SeatReservationService;
import uk.gov.dwp.uc.pairtest.domain.TicketTypeRequest;
import uk.gov.dwp.uc.pairtest.exception.InvalidPurchaseException;
import uk.gov.dwp.uc.pairtest.service.calculator.TicketReservationCalculator;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TicketServiceImpl implements TicketService {

    private int MAX_TICKETS = 20;
    private Logger logger = LoggerFactory.getLogger(TicketServiceImpl.class);

    @Autowired
    private TicketPaymentService ticketPaymentService;
    @Autowired
    private TicketReservationCalculator ticketReservationCalculator;
    @Autowired
    private SeatReservationService seatReservationService;

    /**
     * Should only have private methods other than the one below.
     */
    @Override
    public void purchaseTickets(Long accountId, TicketTypeRequest... ticketTypeRequests) throws InvalidPurchaseException {
        validateTicketPurchase(ticketTypeRequests);
        int totalPrice = ticketReservationCalculator.calculateTotalPrice(ticketTypeRequests);
        int totalSeats = ticketReservationCalculator.calculateTotalSeats(ticketTypeRequests);

        logger.info("Making reservation for accountId: {}, total seats: {}, total price: {}", accountId, totalSeats, totalPrice);
        ticketPaymentService.makePayment(accountId, totalPrice);
        seatReservationService.reserveSeat(accountId, totalSeats);
    }

    private void validateTicketPurchase(TicketTypeRequest[] ticketTypeRequests) {
        List<TicketTypeRequest> totalAdults = Arrays.stream(ticketTypeRequests)
                .filter(request -> request.getTicketType() ==  TicketTypeRequest.Type.ADULT)
                .collect(Collectors.toList());

        int totalTickets = Arrays.stream(ticketTypeRequests).mapToInt(TicketTypeRequest::getNoOfTickets).sum();

        if (totalAdults.size() < 1) {
            throw new InvalidPurchaseException("Purchase must include at least 1 adult");
        }

        if (totalTickets > MAX_TICKETS) {
            throw new InvalidPurchaseException("Exceeded maximum number of tickets");
        }
    }
}
