package store.auroraauction.be.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Set;

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

    private LocalDateTime createdAt;

    private Double final_price;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="jewelry_id",referencedColumnName = "id")
    private Jewelry jewelry;// biến wallet này sẽ trùng  với giá trị  mappedBy trong Class Wallet

    @OneToOne
    @JoinColumn(name = "auction_id")
    @JsonIgnore
    private Auction  auction;

    @ManyToOne
    @JoinColumn(name = "buyer_id")
    @JsonIgnore
    private Account buyer;

    @ManyToOne
    @JoinColumn(name = "staff_id")
    @JsonIgnore
    private Account staff;//staff


}
