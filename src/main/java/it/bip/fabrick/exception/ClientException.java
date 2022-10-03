package it.bip.fabrick.exception;

import it.bip.fabrick.model.dto.ErrorDetail;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
public class ClientException extends RuntimeException {
    private final HttpStatus statusCode;
    private final transient List<ErrorDetail> errors;
}
