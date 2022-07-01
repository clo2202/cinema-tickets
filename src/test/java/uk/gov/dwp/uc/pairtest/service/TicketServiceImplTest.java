package uk.gov.dwp.uc.pairtest.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import thirdparty.paymentgateway.TicketPaymentService;
import thirdparty.seatbooking.SeatReservationService;
import uk.gov.dwp.uc.pairtest.domain.TicketTypeRequest;
import uk.gov.dwp.uc.pairtest.exception.InvalidPurchaseException;
import uk.gov.dwp.uc.pairtest.service.calculator.TicketReservationCalculator;

import java.util.stream.Stream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class TicketServiceImplTest {

    @Mock
    private TicketPaymentService ticketPaymentService;
    @Mock
    private TicketReservationCalculator ticketReservationCalculator;
    @Mock
    private SeatReservationService seatReservationService;
    @InjectMocks
    private TicketServiceImpl underTest = new TicketServiceImpl();

    @Test
    public void shouldThrowAnExceptionWhenMaxTicketsIsBreached() {
        TicketTypeRequest ticketTypeRequest1 = new TicketTypeRequest(TicketTypeRequest.Type.ADULT, 20);
        TicketTypeRequest ticketTypeRequest2 = new TicketTypeRequest(TicketTypeRequest.Type.CHILD, 1);

        InvalidPurchaseException exception =  assertThrows(InvalidPurchaseException.class, () -> {
            underTest.purchaseTickets(1L, ticketTypeRequest1, ticketTypeRequest2);
        });

        assertThat(exception.getMessage(), is("Exceeded maximum number of tickets"));
        verifyNoInteractions(ticketPaymentService);
        verifyNoInteractions(ticketReservationCalculator);
    }

    @ParameterizedTest
    @MethodSource("nonAdultTicketType")
    public void shouldThrowAnExceptionWhenNoAdultTicketPresent(TicketTypeRequest ticketTypeRequest) {
        InvalidPurchaseException exception =  assertThrows(InvalidPurchaseException.class, () -> {
            underTest.purchaseTickets(1L, ticketTypeRequest);
        });

        assertThat(exception.getMessage(), is("Purchase must include at least 1 adult"));
        verifyNoInteractions(ticketPaymentService);
        verifyNoInteractions(ticketReservationCalculator);
    }

    @Test
    public void shouldCallPaymentServiceWithTotalPrice() {
        TicketTypeRequest ticketTypeRequest1 = new TicketTypeRequest(TicketTypeRequest.Type.ADULT, 2);
        when(ticketReservationCalculator.calculateTotalSeats(ticketTypeRequest1)).thenReturn(2);
        when(ticketReservationCalculator.calculateTotalPrice(ticketTypeRequest1)).thenReturn(40);

        underTest.purchaseTickets(1L, ticketTypeRequest1);
        verify(ticketPaymentService).makePayment(1L, 40);
    }

    @Test
    public void shouldCallSeatReservationServiceWithTotalSeats() {
        TicketTypeRequest ticketTypeRequest1 = new TicketTypeRequest(TicketTypeRequest.Type.ADULT, 2);
        when(ticketReservationCalculator.calculateTotalSeats(ticketTypeRequest1)).thenReturn(2);
        when(ticketReservationCalculator.calculateTotalPrice(ticketTypeRequest1)).thenReturn(40);

        underTest.purchaseTickets(1L, ticketTypeRequest1);
        verify(seatReservationService).reserveSeat(1L, 2);
    }

    private static Stream<TicketTypeRequest> nonAdultTicketType() {
        return Stream.of(
                new TicketTypeRequest(TicketTypeRequest.Type.CHILD, 1),
                new TicketTypeRequest(TicketTypeRequest.Type.INFANT, 1)
        );
    }
}