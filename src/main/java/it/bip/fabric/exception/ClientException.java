package it.bip.fabric.exception;

import it.bip.fabric.model.ClientApiErrorDetail;
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
    private final transient List<ClientApiErrorDetail> errors;
}
