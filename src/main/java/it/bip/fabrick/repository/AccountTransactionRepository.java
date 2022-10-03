package it.bip.fabrick.repository;

import it.bip.fabrick.model.entity.AccountTransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountTransactionRepository extends JpaRepository<AccountTransactionEntity, Long> {
    Optional<AccountTransactionEntity> findByAccountId(String accountId);
    Optional<AccountTransactionEntity> findByTransactionId(String transactionId);
    boolean existsByTransactionId(String transactionId);
}
