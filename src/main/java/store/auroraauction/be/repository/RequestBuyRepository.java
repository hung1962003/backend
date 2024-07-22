package store.auroraauction.be.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import store.auroraauction.be.entity.RequestBuy;
@Repository
public interface RequestBuyRepository extends JpaRepository<RequestBuy, Long> {
    RequestBuy findById(long id);
}
