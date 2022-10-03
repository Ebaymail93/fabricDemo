package it.bip.fabrick.model.entity;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Table(name = "account_transaction")
@Entity
public class AccountTransactionEntity {
    @Id
    @Column(name = "transactionId", nullable = false)
    private String transactionId;
    private String accountId;
    private String operationId;
    @Temporal(TemporalType.TIMESTAMP)
    private Date accountingDate;
    @Temporal(TemporalType.TIMESTAMP)
    private Date valueDate;
    @Embedded
    private TransactionType type;
    private BigDecimal amount;
    private String currency;
    private String description;
}


