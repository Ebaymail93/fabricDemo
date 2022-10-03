package it.bip.fabrick.service;

import it.bip.fabrick.model.entity.AccountTransactionEntity;
import it.bip.fabrick.repository.AccountTransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class AccountService {

    @Autowired
    private AccountTransactionRepository accountTransactionRepository;

    public void saveTransactions(List<AccountTransactionEntity> accountTransactions){
        this.accountTransactionRepository.saveAll(accountTransactions);
    }

    public List<AccountTransactionEntity> getTransactions(){
        return this.accountTransactionRepository.findAll();
    }

    public boolean insertIfNotExists(AccountTransactionEntity entity){
        boolean exists = this.accountTransactionRepository.existsByTransactionId(entity.getTransactionId());
        if(!exists){
            this.accountTransactionRepository.save(entity);
            return true;
        }
        return false;
    }

}
