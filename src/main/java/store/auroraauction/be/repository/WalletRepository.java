package store.auroraauction.be.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import store.auroraauction.be.api.WalletAPI;
import store.auroraauction.be.entity.Wallet;
@Repository

public interface WalletRepository extends JpaRepository<Wallet,Long>{
    Wallet findWalletByAccountId(long id);

}
