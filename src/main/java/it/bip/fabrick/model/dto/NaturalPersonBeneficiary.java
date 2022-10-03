package it.bip.fabrick.model.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class NaturalPersonBeneficiary {
    @NotBlank(message = "fiscalcode1 is required.")
    private String fiscalCode1;
    private String fiscalCode2;
    private String fiscalCode3;
    private String fiscalCode4;
    private String fiscalCode5;
}
