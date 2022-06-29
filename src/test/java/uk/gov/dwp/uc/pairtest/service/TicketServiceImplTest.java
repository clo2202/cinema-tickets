package uk.gov.dwp.uc.pairtest.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import uk.gov.dwp.uc.pairtest.domain.TicketTypeRequest;
import uk.gov.dwp.uc.pairtest.exception.InvalidPurchaseException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.*;

public class TicketServiceImplTest {

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




}