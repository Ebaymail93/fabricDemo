package it.bip.fabric.config;

import it.bip.fabric.model.AccountBalance;
import it.bip.fabric.model.AccountTransactionPayload;
import it.bip.fabric.model.MoneyTransferRequest;
import it.bip.fabric.model.MoneyTransfer;
import org.springframework.web.client.HttpClientErrorException;

import java.sql.Date;

public interface FabricClient {

    public AccountBalance getBalance(String timeZone, String accountId);

    public MoneyTransfer createMoneyTransfer(String timeZone, String accountId, MoneyTransferRequest moneyTransferRequest) throws HttpClientErrorException;

    public AccountTransactionPayload getTransactions(String timeZone, String accountId, Date fromAccountingDate, Date toAccountingDate);
}
