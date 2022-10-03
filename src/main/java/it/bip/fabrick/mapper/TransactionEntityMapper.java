package it.bip.fabrick.mapper;

import it.bip.fabrick.model.dto.AccountTransaction;
import it.bip.fabrick.model.dto.AccountTransactionType;
import it.bip.fabrick.model.entity.AccountTransactionEntity;
import it.bip.fabrick.model.entity.TransactionType;

public class TransactionEntityMapper {

    public static AccountTransactionEntity toEntity(AccountTransaction accountTransaction){
        AccountTransactionEntity entity = new AccountTransactionEntity();
        TransactionType transactionType = new TransactionType();
        transactionType.setEnumeration(accountTransaction.getType().getEnumeration());
        transactionType.setValue(accountTransaction.getType().getValue());
        entity.setTransactionId(accountTransaction.getTransactionId());
        entity.setAccountingDate(accountTransaction.getAccountingDate());
        entity.setAmount(accountTransaction.getAmount());
        entity.setCurrency(accountTransaction.getCurrency());
        entity.setValueDate(accountTransaction.getValueDate());
        entity.setDescription(accountTransaction.getDescription());
        entity.setType(transactionType);
        entity.setOperationId(accountTransaction.getOperationId());
        return entity;
    }

    public static AccountTransaction toDto(AccountTransactionEntity accountTransaction){
        AccountTransaction dto = new AccountTransaction();
        AccountTransactionType transactionType = new AccountTransactionType();
        transactionType.setEnumeration(accountTransaction.getType().getEnumeration());
        transactionType.setValue(accountTransaction.getType().getValue());
        dto.setTransactionId(accountTransaction.getTransactionId());
        dto.setAccountingDate(accountTransaction.getAccountingDate());
        dto.setAmount(accountTransaction.getAmount());
        dto.setCurrency(accountTransaction.getCurrency());
        dto.setValueDate(accountTransaction.getValueDate());
        dto.setDescription(accountTransaction.getDescription());
        dto.setType(transactionType);
        dto.setOperationId(accountTransaction.getOperationId());
        return dto;
    }
}
