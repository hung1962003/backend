package store.auroraauction.be.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    private int amountofmoney;


    @ManyToOne
    @JoinColumn(name = "jewelry_id")
    private Jewelry  jewelry;

    private Date  createAt;

    @ManyToOne
    @JoinColumn(name = "buyer_id")
    private Account  account;

    @ManyToOne
    @JoinColumn(name = "auction_id")
    private Auction  auction;

    @ManyToOne
    @JoinColumn(name = "wallet_id")
    private Wallet  wallet;
}
