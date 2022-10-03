package it.bip.fabrick.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;


@Data
public class MoneyTransferRequest {
    @NotNull(message = "The creditor is required.")
    private Creditor creditor;
    @JsonFormat(pattern="yyyy-MM-dd")
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
    private String feeAccountId;
    private TaxRelief taxRelief;
}
