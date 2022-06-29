package uk.gov.dwp.uc.pairtest.service;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import thirdparty.paymentgateway.TicketPaymentService;
import uk.gov.dwp.uc.pairtest.domain.TicketTypeRequest;
import uk.gov.dwp.uc.pairtest.exception.InvalidPurchaseException;
import uk.gov.dwp.uc.pairtest.service.calculator.TicketReservationCalculator;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
public class TicketServiceImplTest {

    @Mock
    private TicketPaymentService ticketPaymentService;
    @Mock
    private TicketReservationCalculator ticketReservationCalculator;
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
    }

    @Test
    public void shouldThrowAnExceptionWhenNoAdultTicketPresent() {
        TicketTypeRequest ticketTypeRequest1 = new TicketTypeRequest(TicketTypeRequest.Type.CHILD, 1);

        InvalidPurchaseException exception =  assertThrows(InvalidPurchaseException.class, () -> {
            underTest.purchaseTickets(1L, ticketTypeRequest1);
        });

        assertThat(exception.getMessage(), is("Purchase must include at least 1 adult"));
    }

    @Test
    public void shouldCallPaymentServiceWithTotalPrice() {
        TicketTypeRequest ticketTypeRequest1 = new TicketTypeRequest(TicketTypeRequest.Type.ADULT, 2);
        when(ticketReservationCalculator.calculateTotalPrice(ticketTypeRequest1)).thenReturn(40);
        underTest.purchaseTickets(1L, ticketTypeRequest1);
        verify(ticketPaymentService).makePayment(1L, 40);
    }

}