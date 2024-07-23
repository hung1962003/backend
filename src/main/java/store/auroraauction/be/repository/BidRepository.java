package store.auroraauction.be.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import store.auroraauction.be.entity.Account;
import store.auroraauction.be.entity.Bid;
import store.auroraauction.be.enums.BidStatusEnum;
import store.auroraauction.be.enums.ThisIsTheHighestBid;

import java.util.List;

@Repository
public interface BidRepository extends JpaRepository<Bid, Long> {
    Bid findBidByAuction_IdAndJewelry_Id(long auctionid, long jewelryId);
    Bid findBidByAuction_IdAndJewelry_IdAndAccount_Id(long auctionid, long jewelryId,long accountId);


    @Query(value = "SELECT b.* FROM bid b " +
            "WHERE b.auction_id = :auctionId " +
            "AND b.jewelry_id = :jewelryId " +
            "AND b.buyerid = :buyerId " +
            "AND b.amountOfMoney = (SELECT MAX(b2.amountOfMoney) FROM bid b2 " +
            "WHERE b2.auction_id = :auctionId " +
            "AND b2.jewelry_id = :jewelryId " +
            "AND b2.buyerid = :buyerId)",nativeQuery = true)
    Bid findMaxBidInAuctionInBuyer_Id(@Param("auctionId") Long auctionId,
                                     @Param("jewelryId") Long jewelryId,
                                     @Param("buyerId") Long buyerId);
    @Query(value="SELECT b.* FROM bid b " +
            "WHERE b.auction_id = :auctionId " +
            "AND b.jewelry_id = :jewelryId " +
            "AND b.amountOfMoney = (SELECT MAX(b2.amountOfMoney) FROM bid b2 " +
            "WHERE b2.auction_id = :auctionId " +
            "AND b2.jewelry_id = :jewelryId )",nativeQuery = true)
    Bid findMaxBidInAuction(@Param("auctionId") Long auctionId,
                                  @Param("jewelryId") Long jewelryId);

    @Query(value="SELECT b.* FROM bid b WHERE b.buyerid = :buyerId",nativeQuery = true )
    List<Bid> findBidByBuyer_Id(@Param("buyerId") Long buyerId);

    List<Bid> findBidByBidStatusEnum(BidStatusEnum bidStatusEnum);

    List<Bid> findBidByThisIsTheHighestBid(ThisIsTheHighestBid isThisTheHighestBid);

    @Query(value="select count(distinct buyerid) from bid where auction_id=: auctionid", nativeQuery = true)
    List<Bid> countUserInAuction(@Param("auctionid") Long auctionid);
}

