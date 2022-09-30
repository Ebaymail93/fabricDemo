package it.bip.fabric.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class ClientApiErrorDetail implements Serializable {
    private String code;
    private String description;
    private String params;

    @Override
    public String toString() {
        return "{" +
                "code='" + code + '\'' +
                ", description='" + description + '\'' +
                ", params='" + params + '\'' +
                '}';
    }
}
