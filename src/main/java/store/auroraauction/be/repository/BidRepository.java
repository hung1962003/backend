package store.auroraauction.be.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import store.auroraauction.be.entity.Bid;

@Repository
public interface BidRepository extends JpaRepository<Bid, Long> {
}
