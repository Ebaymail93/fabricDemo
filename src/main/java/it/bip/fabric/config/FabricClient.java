package it.bip.fabric.config;

import it.bip.fabric.model.dto.AccountBalanceResponse;
import it.bip.fabric.model.dto.AccountTransactionResponse;
import it.bip.fabric.model.dto.MoneyTransferRequest;
import it.bip.fabric.model.dto.MoneyTransferResponse;
import org.springframework.web.client.HttpClientErrorException;

import java.sql.Date;

public interface FabricClient {

    public AccountBalanceResponse getBalance(String timeZone, String accountId);

    public MoneyTransferResponse createMoneyTransfer(String timeZone, String accountId, MoneyTransferRequest moneyTransferRequest) throws HttpClientErrorException;

    public AccountTransactionResponse getTransactions(String timeZone, String accountId, Date fromAccountingDate, Date toAccountingDate);
}
