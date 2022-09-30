package it.bip.fabric.exceptionhandler;

import it.bip.fabric.exception.ClientException;
import it.bip.fabric.model.ErrorResponse;
import it.bip.fabric.model.Violation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class RestTemplateExpectionHandler {
    private static final Logger log = LoggerFactory.getLogger(RestTemplateExpectionHandler.class);

    @ExceptionHandler(value = ClientException.class)
    ResponseEntity<ErrorResponse> handleMyRestTemplateException(ClientException ex) {
        log.error("Si è verificato un errore durante la chiamata all'API: {}", ex.getErrors());
        return new ResponseEntity<>(new ErrorResponse(ex.getErrors()), ex.getStatusCode());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        ErrorResponse error = new ErrorResponse();
        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
            error.getViolations().add(
                    new Violation(fieldError.getField(), fieldError.getDefaultMessage()));
        }
        log.error("Sono stati violati i vincoli presenti sui parametri: {}", error.getViolations());
        return ResponseEntity.badRequest().body(error);
    }
}