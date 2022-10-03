package it.bip.fabrick.model.dto.clientresponse;


import com.fasterxml.jackson.annotation.JsonProperty;
import it.bip.fabrick.model.dto.ErrorDetail;
import lombok.Data;

import java.util.List;

@Data
public abstract class ClientResponse<R> {

    @JsonProperty("status")
    private String status;
    @JsonProperty("errors")
    private List<ErrorDetail> errors;
    @JsonProperty("payload")
    private R payload;

}
