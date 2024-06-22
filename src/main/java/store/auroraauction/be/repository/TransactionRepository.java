package store.auroraauction.be.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import store.auroraauction.be.entity.Transaction;
import store.auroraauction.be.enums.TransactionEnum;

import java.util.List;

public interface TransactionRepository  extends JpaRepository<Transaction,Long> {
    Transaction findById(long id);

    List<Transaction> findTransactionByTransactionEnum(TransactionEnum status);
}
