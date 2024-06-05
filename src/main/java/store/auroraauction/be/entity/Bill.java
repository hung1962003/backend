package store.auroraauction.be.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "bill")
public class Bill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private Date createdAt;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="jewelry_id",referencedColumnName = "id")
    private Jewelry jewelry;// biến wallet này sẽ trùng  với giá trị  mappedBy trong Class Wallet

    @ManyToOne
    @JoinColumn(name = "auction_id")
    private Auction  auction;

//    @ManyToOne
//    @JoinColumn(name = "buyer_id")
//    private Account  account;
//
//    @ManyToOne
//    @JoinColumn(name = "seller_id")
//    private Account  account;
//
//    @ManyToOne
//    @JoinColumn(name = "staff_id")
//    private Account  account;


}
