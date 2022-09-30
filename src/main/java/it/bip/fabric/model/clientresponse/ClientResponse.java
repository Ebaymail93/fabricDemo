package it.bip.fabric.model.clientresponse;


import com.fasterxml.jackson.annotation.JsonProperty;
import it.bip.fabric.model.ClientApiErrorDetail;
import lombok.Data;

import java.util.List;

@Data
public abstract class ClientResponse <R> {

    @JsonProperty("status")
    private String status;
    @JsonProperty("errors")
    private List<ClientApiErrorDetail> errors;
    @JsonProperty("payload")
    private R payload;

}
