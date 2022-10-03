package it.bip.fabrick.config.interceptor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class ClientInterceptor implements ClientHttpRequestInterceptor {

    @Value("${external-api.apiKey}")
    private String apiKey;

    @Value("${external-api.authSchema}")
    private String authSchema;

    @Value("${external-api.timeZone:Europe/Rome}")
    private String timeZone;

    /**
     * Per ogni request verso le api Fabric valorizzo gli header Auth-Schema, Api-Key e TimeZone (se non valorizzata dall'utente).
     *
     * @param request
     * @param body
     * @param execution
     * @return
     * @throws IOException
     */
    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        request.getHeaders().set("Auth-Schema", authSchema);
        request.getHeaders().set("Api-Key", apiKey);
        request.getHeaders().addIfAbsent("X-Time-Zone", timeZone);
        return execution.execute(request, body);
    }
}