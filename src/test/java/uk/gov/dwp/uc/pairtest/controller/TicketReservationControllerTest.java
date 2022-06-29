package uk.gov.dwp.uc.pairtest.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import uk.gov.dwp.uc.pairtest.TicketService;
import uk.gov.dwp.uc.pairtest.domain.TicketTypeRequest;


import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = TicketReservationController.class)
public class TicketReservationControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private TicketService ticketServiceMock;

    @Test
    public void shouldReturn200WithValidInput() throws Exception {
        String requestBody = "{\"accountId\": 1, \"ticketTypeRequests\": [{\"noOfTickets\": 2, \"type\": \"ADULT\"}]}";
        ArgumentCaptor<TicketTypeRequest> captor = ArgumentCaptor.forClass(TicketTypeRequest.class);

        mvc.perform(post("/book-tickets").content(requestBody)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(200))
                .andExpect(jsonPath("$.message").value("Successfully reserved ticket(s) for account id: 1"));

        verify(ticketServiceMock).purchaseTickets(eq(1L), captor.capture());
        assertThat(captor.getValue().getNoOfTickets(), is(2));
        assertThat(captor.getValue().getTicketType(), is(TicketTypeRequest.Type.ADULT));
    }

    @Test
    public void shouldReturn400WhenAccountIdIsInvalid() throws Exception {
        String requestBody = "{\"accountId\": 0, \"ticketTypeRequests\": [{\"noOfTickets\": 2, \"type\": \"ADULT\"}]}";

        mvc.perform(post("/book-tickets")
                .contentType("application/json")
                .content(requestBody))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.message").value("Request validation failed"))
                .andExpect(jsonPath("$.errors.[0]").value("account id cannot be less than 0"));
        verifyNoInteractions(ticketServiceMock);
    }

    @Test
    public void shouldReturn400WhenAccountIdIsNull() throws Exception {
        String requestBody = "{\"ticketTypeRequests\": [{\"noOfTickets\": 2, \"type\": \"ADULT\"}]}";

        mvc.perform(post("/book-tickets")
                .contentType("application/json")
                .content(requestBody))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.message").value("Request validation failed"))
                .andExpect(jsonPath("$.errors.[0]").value("account id cannot be null"));
        verifyNoInteractions(ticketServiceMock);
    }

    @Test
    public void shouldReturn400WhenTicketTypeListIsEmpty() throws Exception {
        String requestBody = "{\"accountId\": 1, \"ticketTypeRequests\": []}";

        mvc.perform(post("/book-tickets")
                .contentType("application/json")
                .content(requestBody))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.message").value("Request validation failed"))
                .andExpect(jsonPath("$.errors.[0]").value("ticket type list cannot be empty"));
        verifyNoInteractions(ticketServiceMock);
    }

    @Test
    public void shouldReturn400WhenTicketTypeIsNull() throws Exception {
        String requestBody = "{\"accountId\": 1, \"ticketTypeRequests\": [{\"noOfTickets\": 2}]}";

        mvc.perform(post("/book-tickets")
                .contentType("application/json")
                .content(requestBody))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.message").value("Request validation failed"))
                .andExpect(jsonPath("$.errors.[0]").value("ticket type cannot be null"));
        verifyNoInteractions(ticketServiceMock);
    }
}
