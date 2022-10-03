package it.bip.fabrick.model.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class TaxRelief {
    private String taxReliefId;
    @NotNull(message = "isCondoUpgrade field is required.")
    private Boolean isCondoUpgrade;
    @NotBlank(message = "Creditor fiscal code is required.")
    private String creditorFiscalCode;
    @NotBlank(message = "Beneficiary type is required.")
    private String beneficiaryType;
    private NaturalPersonBeneficiary naturalPersonBeneficiary;
    private LegalPersonBeneficiary legalPersonBeneficiary;
}
