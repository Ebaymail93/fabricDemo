package it.bip.fabrick.mapper;

import it.bip.fabrick.model.dto.clientresponse.ClientResponse;

public class ClientModelMapper<R> {

    public R clientEntityToResource(ClientResponse<R> entity) {
        if ( entity == null ) {
            return null;
        }
        return entity.getPayload();
    }
}
