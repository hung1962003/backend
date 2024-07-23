package store.auroraauction.be.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import store.auroraauction.be.entity.RequestBuy;

import java.util.List;

@Repository
public interface RequestBuyRepository extends JpaRepository<RequestBuy, Long> {
    RequestBuy findById(long id);
    List<RequestBuy> findRequestBuyByAccountId(long id);
}
