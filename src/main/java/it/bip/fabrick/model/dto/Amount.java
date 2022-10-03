package it.bip.fabrick.model.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class Amount {
    private BigDecimal debtorAmount;
    private String debtorCurrency;
    private BigDecimal creditorAmount;
    private String creditorCurrency;
    private Date creditorCurrencyDate;
    private Long exchangeRate;
}
