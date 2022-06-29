package uk.gov.dwp.uc.pairtest.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import thirdparty.paymentgateway.TicketPaymentService;
import thirdparty.paymentgateway.TicketPaymentServiceImpl;
import thirdparty.seatbooking.SeatReservationService;
import uk.gov.dwp.uc.pairtest.service.TicketService;
import uk.gov.dwp.uc.pairtest.service.TicketServiceImpl;
import uk.gov.dwp.uc.pairtest.service.calculator.TicketReservationCalculator;
import uk.gov.dwp.uc.pairtest.service.calculator.TicketReservationCalculatorImpl;

@Configuration
public class AppConfig {
    @Bean
    public TicketPaymentService ticketPaymentService() {
        return new TicketPaymentServiceImpl();
    }

    @Bean
    public SeatReservationService seatReservationService() {
        return ((accountId, totalSeatsToAllocate) -> {});
    }

    @Bean
    public TicketReservationCalculator ticketReservationCalculator() {
        return new TicketReservationCalculatorImpl();
    }

    @Bean
    public TicketService ticketService() {
        return new TicketServiceImpl();
    }
}
