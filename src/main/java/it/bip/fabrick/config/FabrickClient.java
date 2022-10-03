package it.bip.fabrick.config;

import it.bip.fabrick.model.dto.AccountBalanceResponse;
import it.bip.fabrick.model.dto.AccountTransactionResponse;
import it.bip.fabrick.model.dto.MoneyTransferRequest;
import it.bip.fabrick.model.dto.MoneyTransferResponse;
import org.springframework.web.client.HttpClientErrorException;

import java.sql.Date;

public interface FabrickClient {

    public AccountBalanceResponse getBalance(String timeZone, String accountId);

    public MoneyTransferResponse createMoneyTransfer(String timeZone, String accountId, MoneyTransferRequest moneyTransferRequest) throws HttpClientErrorException;

    public AccountTransactionResponse getTransactions(String timeZone, String accountId, Date fromAccountingDate, Date toAccountingDate);
}
