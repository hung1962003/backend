package store.auroraauction.be.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import store.auroraauction.be.enums.StatusJewelryEnum;

import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "jewelry")
public class Jewelry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    long id;
    String name;
    double last_price;
    int low_estimated_price;
    int high_estimated_price;
    double weight;
    @Column(columnDefinition = "TEXT") // create a column in sql with data  type is TEXT
    String description;
    @Column(columnDefinition = "TEXT") // create a column in sql with data  type is TEXT
    String ConditionReport;
    @Enumerated(value = EnumType.STRING)
    StatusJewelryEnum statusJewelryEnum;
    String images;

//    @ManyToMany(mappedBy ="jewelries",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
//    @JsonBackReference
//    Set<Auction> auctions;

    @OneToMany(mappedBy = "jewelry", cascade = CascadeType.ALL)
    @JsonIgnore
    private  Set<Auction> auctions;

    @ManyToMany
    @JoinTable(name = "cart_jewelry",
            joinColumns = @JoinColumn(name="jewelry_id"),
            inverseJoinColumns = @JoinColumn(name="cart_id"))
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class , property = "id")
            @JsonIgnore
    Set<Cart> carts;


    @OneToOne(mappedBy = "jewelry")
    @JsonIgnore
    private RequestBuy requestBuy;


    @OneToOne(mappedBy = "jewelry")
    @JsonIgnore
    private Order order;

    @ManyToOne
    @JoinColumn(name = "sell_id")

    private Account account;


    @ManyToOne
    @JoinColumn(name = "category_id")
    Category category;

    

    @OneToMany(mappedBy = "jewelry", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Bid> bids;

    @OneToOne(mappedBy = "jewelry")
    @JsonIgnore
    private Process process;
}
