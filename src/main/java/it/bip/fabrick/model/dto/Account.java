package it.bip.fabrick.model.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class Account {
    @NotBlank(message = "Account code is required.")
    private String accountCode;
    private String bicCode;
}
