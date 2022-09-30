package it.bip.fabric.repository;

import it.bip.fabric.model.entity.AccountTransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountTransactionRepository extends JpaRepository<AccountTransactionEntity, Long> {
    Optional<AccountTransactionEntity> findByAccountId(String accountId);

    Optional<AccountTransactionEntity> findByTransactionId(String accountId);
}
