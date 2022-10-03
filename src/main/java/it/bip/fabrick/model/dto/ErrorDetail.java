package it.bip.fabrick.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class ErrorDetail implements Serializable {
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

    public ErrorDetail(String code, String description) {
        this.code = code;
        this.description = description;
    }
}
