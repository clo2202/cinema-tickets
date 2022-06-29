package uk.gov.dwp.uc.pairtest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uk.gov.dwp.uc.pairtest.TicketService;
import uk.gov.dwp.uc.pairtest.domain.TicketReservationRequest;
import uk.gov.dwp.uc.pairtest.domain.TicketReservationResponse;
import uk.gov.dwp.uc.pairtest.domain.TicketTypeRequest;

import javax.validation.Valid;

@RestController
public class TicketReservationController {

    @Autowired
    TicketService ticketService;

    @PostMapping("/book-tickets")
    public ResponseEntity<TicketReservationResponse> bookTickets(@Valid @RequestBody TicketReservationRequest ticketReservationRequest) {
        ticketService.purchaseTickets(ticketReservationRequest.getAccountId(), ticketReservationRequest.getTicketTypeRequests().toArray(TicketTypeRequest[]::new));

        String responseMessage = String.format("Successfully reserved ticket(s) for account id: %s", ticketReservationRequest.getAccountId());
        TicketReservationResponse response = new TicketReservationResponse(HttpStatus.OK.value(), responseMessage);
        return new ResponseEntity(response, HttpStatus.OK);
    }

}
