package it.bip.fabric.model.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;


@Data
public class MoneyTransferRequest {
    @NotNull(message = "The creditor is required.")
    private Creditor creditor;
    private Date executionDate;
    private String uri;
    @NotBlank(message = "Description is required.")
    private String description;
    @NotNull(message = "Amount is required.")
    private BigDecimal amount;
    @NotBlank(message = "Currency is required.")
    private String currency;
    private Boolean isUrgent;
    private Boolean isInstant;
    private String feeType;
    private String feeAccountID;
    private TaxRelief taxRelief;
}
