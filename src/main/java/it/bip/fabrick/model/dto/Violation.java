package it.bip.fabrick.model.dto;

import lombok.Data;

@Data
public
class Violation {
    private final String fieldName;
    private final String message;

    @Override
    public String toString() {
        return "{" +
                "fieldName='" + fieldName + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
