package it.bip.fabric.mapper;

import it.bip.fabric.model.clientresponse.ClientResponse;

public class ClientModelMapper<R> {


    public R entityToResource(ClientResponse<R> entity) {
        if ( entity == null ) {
            return null;
        }
        return entity.getPayload();
    }
}
