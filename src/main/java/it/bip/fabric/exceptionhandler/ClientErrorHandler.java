package it.bip.fabric.exceptionhandler;

import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.DefaultResponseErrorHandler;

public class ClientErrorHandler extends DefaultResponseErrorHandler {

    @Override
    public void handleError(ClientHttpResponse response)  {
        /*
        Gestisco l'errore ricevuto dal client per recuperare il messaggio.
         */
    }
}