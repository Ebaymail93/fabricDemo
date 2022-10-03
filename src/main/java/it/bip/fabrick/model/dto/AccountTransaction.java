package it.bip.fabrick.model.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class AccountTransaction {
    private String transactionId;
    private String operationId;
    private Date accountingDate;
    private Date valueDate;
    private AccountTransactionType type;
    private BigDecimal amount;
    private String currency;
    private String description;
}
