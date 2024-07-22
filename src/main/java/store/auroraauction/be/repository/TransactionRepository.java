package store.auroraauction.be.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import store.auroraauction.be.entity.Transaction;
import store.auroraauction.be.enums.TransactionEnum;

import java.util.List;
@Repository
public interface TransactionRepository  extends JpaRepository<Transaction,Long> {
    Transaction findById(long id);

    List<Transaction> findTransactionByTransactionEnum(TransactionEnum status);
    List<Transaction> findTransactionByWallet_Id(long walletId);
}
