package store.auroraauction.be.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import store.auroraauction.be.entity.Auction;
import store.auroraauction.be.enums.AuctionsStatusEnum;

import java.util.List;

@Repository
public interface AuctionRepository extends JpaRepository<Auction, Long> {
    Auction findAuctionById(long id);
    List<Auction> findByAuctionsStatusEnum(  AuctionsStatusEnum auctionsStatusEnum);
    @Query(value="SELECT  COUNT(DISTINCT b.buyerid) \n" +
            "FROM auction a\n" +
            "inner JOIN bid b ON b.auction_id = a.jewelry_id\n" +
            "WHERE a.id = :auctionId \n" ,nativeQuery = true)
    long countUserByAuctionId(@Param("auctionId") Long auctionId);
    @Query(value = "SELECT a.* FROM auction a\n" +
            "where a.auctions_status_enum = :role",nativeQuery = true)
    List<Auction> findAuctionByAuctions_Status_Enum(@Param("role") String role);
}
