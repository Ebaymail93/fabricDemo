package it.bip.fabric.model;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class MoneyTransfer {
    private String moneyTransferId;
    private String status;
    private String direction;
    private Creditor creditor;
    private Debtor debtor;
    private String cro;
    private String uri;
    private String trn;
    private String description;
    private Date createdDatetime;
    private Date accountedDatetime;
    private Date debtorValueDate;
    private Date creditorValueDate;
    private Amount amount;
    private Boolean isUrgent;
    private Boolean isInstant;
    private String feeType;
    private String feeAccountId;
    private List<Fee> fees;
    private Boolean hasTaxRelief;
}
