package uk.gov.dwp.uc.pairtest.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {

    private Logger logger = LoggerFactory.getLogger(ControllerExceptionHandler.class);

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status,
                                                                  WebRequest request) {
        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(x -> x.getDefaultMessage())
                .collect(Collectors.toList());

        TicketApiError errorResponse = new TicketApiError(status.value(), "Request validation failed", errors);

        logger.error("Request validation failed {}", ex.getMessage());

        return new ResponseEntity(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidPurchaseException.class)
    public ResponseEntity<Object> handleInvalidPurchaseException(InvalidPurchaseException ex) {

        TicketApiError errorResponse = new TicketApiError(400, "Invalid purchase request", List.of(ex.getMessage()));

        logger.error("Invalid purchase request {}", ex.getMessage());

        return new ResponseEntity(errorResponse, HttpStatus.BAD_REQUEST);
    }
}
