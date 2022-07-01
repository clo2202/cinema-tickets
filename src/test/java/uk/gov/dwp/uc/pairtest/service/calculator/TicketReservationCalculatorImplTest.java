package uk.gov.dwp.uc.pairtest.service.calculator;

import org.junit.jupiter.api.Test;
import uk.gov.dwp.uc.pairtest.domain.TicketTypeRequest;

import static org.junit.jupiter.api.Assertions.*;

public class TicketReservationCalculatorImplTest {

    private TicketReservationCalculatorImpl underTest = new TicketReservationCalculatorImpl();

    @Test
    public void shouldCalculateTotalTicketPrice() {
        TicketTypeRequest request1 = new TicketTypeRequest(TicketTypeRequest.Type.ADULT, 2);
        TicketTypeRequest request2 = new TicketTypeRequest(TicketTypeRequest.Type.CHILD, 1);
        TicketTypeRequest request3 = new TicketTypeRequest(TicketTypeRequest.Type.INFANT, 1);

        int result = underTest.calculateTotalPrice(request1, request2, request3);
        assertEquals(result, 50);
    }

    @Test
    public void shouldCalculateTotalNoOfSeats() {
        TicketTypeRequest request1 = new TicketTypeRequest(TicketTypeRequest.Type.ADULT, 2);
        TicketTypeRequest request2 = new TicketTypeRequest(TicketTypeRequest.Type.CHILD, 1);

        int result = underTest.calculateTotalSeats(request1, request2);
        assertEquals(result, 3);
    }

    @Test
    public void shouldNotAllocateInfantSeat() {
        TicketTypeRequest request1 = new TicketTypeRequest(TicketTypeRequest.Type.ADULT, 2);
        TicketTypeRequest request2 = new TicketTypeRequest(TicketTypeRequest.Type.INFANT, 1);

        int result = underTest.calculateTotalSeats(request1, request2);
        assertEquals(result, 2);
    }

}