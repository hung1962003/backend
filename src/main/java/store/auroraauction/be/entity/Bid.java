package store.auroraauction.be.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import store.auroraauction.be.enums.BidStatusEnum;
import store.auroraauction.be.enums.ReturnMoneyStatusEnum;
import store.auroraauction.be.enums.ThisIsTheHighestBid;


import java.util.Date;
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "bid")
public class Bid {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long id;
    private double amountofmoney;

    private Double amountofadd;

    private Date createAt;

    @Enumerated(value = EnumType.STRING)
    private BidStatusEnum bidStatusEnum;

    @Enumerated(value = EnumType.STRING)
    private ThisIsTheHighestBid thisIsTheHighestBid;

    @Enumerated(value = EnumType.STRING)
    private ReturnMoneyStatusEnum returnMoneyStatusEnum;

    @ManyToOne
    @JoinColumn(name = "jewelry_id")
    @JsonIgnore
    private Jewelry  jewelry;

    @ManyToOne
    @JoinColumn(name = "buyerid")
    private Account  account;

    @ManyToOne
    @JoinColumn(name = "auction_id")
    @JsonIgnore
    private Auction  auction;

    @ManyToOne
    @JoinColumn(name = "wallet_id")
    @JsonIgnore
    private Wallet  wallet;

    @OneToOne(mappedBy = "bid",cascade = CascadeType.ALL)
    private SystemProfit systemProfit;

}
