package it.bip.fabric.exceptionhandler;

import com.fasterxml.jackson.databind.ObjectMapper;
import it.bip.fabric.exception.ClientException;
import it.bip.fabric.model.dto.clientresponse.ErrorClientResponse;
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