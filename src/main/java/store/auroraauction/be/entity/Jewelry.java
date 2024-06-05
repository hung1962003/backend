package store.auroraauction.be.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;
    @Column(columnDefinition = "TEXT") // create a column in sql with data  type is TEXT
    String description;
    int last_price;
    int low_estimated_price;
    int high_estimated_price;

    @OneToOne(mappedBy = "jewelry")
    private Bill bill;

    @ManyToOne
    @JoinColumn(name = "sell_id")
    private Account  account;

    String image;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "category_id",referencedColumnName = "id")
    Category category;

    String title;
    @Enumerated(value = EnumType.STRING)
    StatusJewelryEnum statusJewelryEnum;
    @Column(name = "jewelry_condition")
    boolean jewelry_condition;

    @Column(columnDefinition = "TEXT") // create a column in sql with data  type is TEXT
    String ConditionReport;


    @ManyToMany
    @JoinTable(name = "cart_jewelry",
            joinColumns = @JoinColumn(name="jewelry_id"),
            inverseJoinColumns = @JoinColumn(name="cart_id"))
    @JsonManagedReference
    Set<Cart> carts;


    @OneToMany(mappedBy = "jewelry", cascade = CascadeType.ALL)
    private List<Bid> bids;


}
