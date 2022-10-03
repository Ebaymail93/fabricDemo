package it.bip.fabrick.model.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class Creditor {
    @NotBlank(message = "Creditor name is required.")
    private String name;
    @NotNull(message = "Creditor account is required.")
    private Account account;
    private Address address;
}
