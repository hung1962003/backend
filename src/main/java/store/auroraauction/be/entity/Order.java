package store.auroraauction.be.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private Date createdAt;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="jewelry_id",referencedColumnName = "id")
    private Jewelry jewelry;// biến wallet này sẽ trùng  với giá trị  mappedBy trong Class Wallet

    @ManyToOne
    @JoinColumn(name = "auction_id")
    @JsonIgnore
    private Auction  auction;



    @ManyToOne
    @JoinColumn(name = "seller_id", referencedColumnName = "id")
    @JsonIgnore
    private Account seller;

    @ManyToOne
    @JoinColumn(name = "buyer_id", referencedColumnName = "id")
    @JsonIgnore
    private Account buyer;

    @ManyToOne
    @JoinColumn(name = "staff_id", referencedColumnName = "id")
    @JsonIgnore
    private Account staff;



}
