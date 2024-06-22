package store.auroraauction.be.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import store.auroraauction.be.entity.Auction;
@Repository
public interface AuctionRepository extends JpaRepository<Auction, Long> {
    Auction findAuctionByName(String name);
}
