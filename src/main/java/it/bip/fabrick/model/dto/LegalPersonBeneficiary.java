package it.bip.fabrick.model.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class LegalPersonBeneficiary {
    @NotBlank(message = "fiscalCode is required.")
    private String fiscalCode;
    private String legalRepresentativeFiscalCode;
}
