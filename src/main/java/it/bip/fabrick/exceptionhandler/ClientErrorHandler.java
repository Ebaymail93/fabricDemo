package it.bip.fabrick.exceptionhandler;

import com.fasterxml.jackson.databind.ObjectMapper;
import it.bip.fabrick.exception.ClientException;
import it.bip.fabrick.model.dto.clientresponse.ErrorClientResponse;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.DefaultResponseErrorHandler;

import java.io.IOException;

public class ClientErrorHandler extends DefaultResponseErrorHandler {


    @Override
    public void handleError(ClientHttpResponse response) throws IOException {
        ErrorClientResponse errorResponse = new ObjectMapper().readValue(response.getBody(), ErrorClientResponse.class);
        throw new ClientException(response.getStatusCode(), errorResponse.getErrors());
    }
}