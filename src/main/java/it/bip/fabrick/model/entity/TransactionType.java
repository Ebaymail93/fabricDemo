package it.bip.fabrick.model.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Data
public class TransactionType implements Serializable {
    @Column(name = "transaction_type_enum")
    private String enumeration;
    @Column(name = "transaction_type_value")
    private String value;

}
