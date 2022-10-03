package it.bip.fabrick.model.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Data
public class ErrorResponse {

    private String timestamp;

    private List<ErrorDetail> errors;

    private List<Violation> violations;

    {
        this.timestamp = DateTimeFormatter.ISO_LOCAL_DATE_TIME.format(LocalDateTime.now());
        this.violations = new ArrayList<>();
    }

    public ErrorResponse(List<ErrorDetail> errors) {
        this.errors = errors;
    }

    public ErrorResponse() {
        this.errors = new ArrayList<>();
    }
}
