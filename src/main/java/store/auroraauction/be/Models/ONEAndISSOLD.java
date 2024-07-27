package store.auroraauction.be.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import store.auroraauction.be.enums.AuctionsStatusEnum;
import store.auroraauction.be.enums.BidStatusEnum;
import store.auroraauction.be.enums.ReturnMoneyStatusEnum;
import store.auroraauction.be.enums.ThisIsTheHighestBid;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor

@SqlResultSetMapping(
        name = "ONEAndISSOLDMapping",
        classes = @ConstructorResult(
                targetClass = ONEAndISSOLD.class,
                columns = {
                        @ColumnResult(name = "bid_id", type = Long.class),
                        @ColumnResult(name = "amountofadd", type = Double.class),
                        @ColumnResult(name = "amountofmoney", type = Double.class),
                        @ColumnResult(name = "bid_status_enum", type = String.class),
                        @ColumnResult(name = "create_at", type = LocalDateTime.class),
                        @ColumnResult(name = "return_money_status_enum", type = String.class),
                        @ColumnResult(name = "this_is_the_highest_bid", type = String.class),
                        @ColumnResult(name = "buyerid", type = Long.class),
                        @ColumnResult(name = "bid_auction_id", type = Long.class),
                        @ColumnResult(name = "bid_jewelry_id", type = Long.class),
                        @ColumnResult(name = "wallet_id", type = Long.class),
                        @ColumnResult(name = "auction_id", type = Long.class),
                        @ColumnResult(name = "auctions_status_enum", type = String.class),
                        @ColumnResult(name = "description", type = String.class),
                        @ColumnResult(name = "end_date", type = LocalDateTime.class),
                        @ColumnResult(name = "image", type = String.class),
                        @ColumnResult(name = "min_price_before_start", type = Double.class),
                        @ColumnResult(name = "auction_name", type = String.class),
                        @ColumnResult(name = "start_date", type = LocalDateTime.class),
                        @ColumnResult(name = "total_user", type = Long.class),
                        @ColumnResult(name = "staff_id", type = Long.class),
                        @ColumnResult(name = "auction_jewelry_id", type = Long.class)
                }
        )
)
@NamedNativeQuery(
        name = "ONEAndISSOLD.findBidByONEAndISSOLD",
        query = "SELECT b.id AS bid_id, b.amountofadd, b.amountofmoney, b.bid_status_enum, b.create_at, b.return_money_status_enum, b.this_is_the_highest_bid, b.buyerid, b.auction_id AS bid_auction_id, b.jewelry_id AS bid_jewelry_id, b.wallet_id, " +
                "a.id AS auction_id, a.auctions_status_enum, a.description, a.end_date, a.image, a.min_price_before_start, a.name AS auction_name, a.start_date, a.total_user, a.staff_id, a.jewelry_id AS auction_jewelry_id " +
                "FROM bid b " +
                "JOIN auction a ON b.auction_id = a.id " +
                "WHERE b.auction_id = :auctionId " +
                "AND b.this_is_the_highest_bid = 'ONE' " +
                "AND a.auctions_status_enum = 'ISSOLD'",
        resultSetMapping = "ONEAndISSOLDMapping"
)
public class ONEAndISSOLD {
    @Id
    private long bid_id;
    private double amountofadd;
    private double amountofmoney;
    private String bid_status_enum;  // Changed to String
    private LocalDateTime create_at;
    private String return_money_status_enum;  // Changed to String
    private String this_is_the_highest_bid;  // Changed to String
    private long buyerid;
    private long bid_auction_id;
    private long bid_jewelry_id;
    private long wallet_id;
    private long auction_id;
    private String auctions_status_enum;  // Changed to String
    @Column(columnDefinition = "TEXT")
    private String description;
    private LocalDateTime end_date;
    private String image;
    private double min_price_before_start;
    private String auction_name;
    private LocalDateTime start_date;
    private long total_user;
    private long staff_id;
    private long auction_jewelry_id;

    public ONEAndISSOLD(long bid_id, double amountofadd, double amountofmoney, String bid_status_enum, LocalDateTime create_at, String return_money_status_enum, String this_is_the_highest_bid, long buyerid, long bid_auction_id, long bid_jewelry_id, long wallet_id, long auction_id, String auctions_status_enum, String description, LocalDateTime end_date, String image, double min_price_before_start, String auction_name, LocalDateTime start_date, long total_user, long staff_id, long auction_jewelry_id) {
        this.bid_id = bid_id;
        this.amountofadd = amountofadd;
        this.amountofmoney = amountofmoney;
        this.bid_status_enum = bid_status_enum;
        this.create_at = create_at;
        this.return_money_status_enum = return_money_status_enum;
        this.this_is_the_highest_bid = this_is_the_highest_bid;
        this.buyerid = buyerid;
        this.bid_auction_id = bid_auction_id;
        this.bid_jewelry_id = bid_jewelry_id;
        this.wallet_id = wallet_id;
        this.auction_id = auction_id;
        this.auctions_status_enum = auctions_status_enum;
        this.description = description;
        this.end_date = end_date;
        this.image = image;
        this.min_price_before_start = min_price_before_start;
        this.auction_name = auction_name;
        this.start_date = start_date;
        this.total_user = total_user;
        this.staff_id = staff_id;
        this.auction_jewelry_id = auction_jewelry_id;
    }

    // Convert string fields to enum in getter methods
    public BidStatusEnum getBid_status_enum() {
        return BidStatusEnum.valueOf(bid_status_enum);
    }

    public ReturnMoneyStatusEnum getReturn_money_status_enum() {
        return ReturnMoneyStatusEnum.valueOf(return_money_status_enum);
    }

    public ThisIsTheHighestBid getThis_is_the_highest_bid() {
        return ThisIsTheHighestBid.valueOf(this_is_the_highest_bid);
    }

    public AuctionsStatusEnum getAuctions_status_enum() {
        return AuctionsStatusEnum.valueOf(auctions_status_enum);
    }
}
