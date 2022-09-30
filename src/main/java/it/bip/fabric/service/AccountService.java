package it.bip.fabric.service;

import it.bip.fabric.model.entity.AccountTransactionEntity;
import it.bip.fabric.repository.AccountTransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountService {

    @Autowired
    private AccountTransactionRepository accountTransactionRepository;

    public void saveTransactions(List<AccountTransactionEntity> accountTransactions){
        this.accountTransactionRepository.saveAll(accountTransactions);
    }

    public List<AccountTransactionEntity> getTransactions(){
        return this.accountTransactionRepository.findAll();
    }

}
