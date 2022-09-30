package it.bip.fabric.config;

import it.bip.fabric.model.dto.AccountBalance;
import it.bip.fabric.model.dto.AccountTransactionPayload;
import it.bip.fabric.model.dto.MoneyTransferRequest;
import it.bip.fabric.model.dto.MoneyTransfer;
import org.springframework.web.client.HttpClientErrorException;

import java.sql.Date;

public interface FabricClient {

    public AccountBalance getBalance(String timeZone, String accountId);

    public MoneyTransfer createMoneyTransfer(String timeZone, String accountId, MoneyTransferRequest moneyTransferRequest) throws HttpClientErrorException;

    public AccountTransactionPayload getTransactions(String timeZone, String accountId, Date fromAccountingDate, Date toAccountingDate);
}
