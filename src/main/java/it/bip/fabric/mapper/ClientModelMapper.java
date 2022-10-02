package it.bip.fabric.mapper;

import it.bip.fabric.model.dto.clientresponse.ClientResponse;

public class ClientModelMapper<R> {

    public R clientEntityToResource(ClientResponse<R> entity) {
        if ( entity == null ) {
            return null;
        }
        return entity.getPayload();
    }
}
