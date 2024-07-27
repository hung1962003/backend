package store.auroraauction.be.repository;

import jakarta.persistence.SqlResultSetMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import store.auroraauction.be.Models.ONEAndISSOLD;
import store.auroraauction.be.entity.Account;
import store.auroraauction.be.entity.Bid;
import store.auroraauction.be.enums.BidStatusEnum;
import store.auroraauction.be.enums.ThisIsTheHighestBid;

import java.util.List;

@Repository
public interface BidRepository extends JpaRepository<Bid, Long> {

    Bid findBidByAuction_IdAndJewelry_Id(long auctionid, long jewelryId);
    Bid findBidByAuction_IdAndJewelry_IdAndAccount_Id(long auctionid, long jewelryId,long accountId);
    List<Bid> findBidByAuction_Id(long auctionid);

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

    @Query(value="select count(distinct buyerid) from bid where auction_id= :auctionid", nativeQuery = true)
    Long countUserInAuction(@Param("auctionid") Long auctionid);
//    @Query(value= "SELECT b.id AS bid_id, b.amountofadd, b.amountofmoney, b.bid_status_enum, b.create_at, b.return_money_status_enum, b.this_is_the_highest_bid, b.buyerid, b.auction_id AS bid_auction_id, b.jewelry_id AS bid_jewelry_id, b.wallet_id, " +
//            "a.id AS auction_id, a.auctions_status_enum, a.description, a.end_date, a.image, a.min_price_before_start, a.name AS auction_name, a.start_date, a.total_user, a.staff_id, a.jewelry_id AS auction_jewelry_id " +
//            "FROM bid b " +
//            "JOIN auction a ON b.auction_id = a.id " +
//            "WHERE b.auction_id = :auctionId " +
//            "AND b.this_is_the_highest_bid = 'ONE' " +
//            "AND a.auctions_status_enum = 'ISSOLD'", nativeQuery = true)
@Query(name = "ONEAndISSOLD.findBidByONEAndISSOLD", nativeQuery = true)
List<ONEAndISSOLD> findBidByONEAndISSOLD(@Param("auctionId") Long auctionId);
}

