package store.auroraauction.be.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.format.annotation.DateTimeFormat;
import store.auroraauction.be.enums.AuctionsStatusEnum;

import java.sql.Time;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name="auction")
public class Auction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;
    String name;
    @Column(columnDefinition = "TEXT") // create a column in sql with data  type is TEXT
    String description;

    LocalDateTime  start_date;
//    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd")
    LocalDateTime  end_date;
    String title;
    String image;

    long totalUser;
    @Enumerated(EnumType.STRING)
    AuctionsStatusEnum auctionsStatusEnum;


//    @ManyToMany( fetch = FetchType.EAGER)//
//    @JoinTable(name = "jewelry_auction",
//            joinColumns = @JoinColumn(name="jewelry_id"),
//            inverseJoinColumns = @JoinColumn(name="auction_id"))
//    @JsonManagedReference
//    Set<Jewelry> jewelries;

    @ManyToOne
    @JoinColumn(name = "jewelry_id")
    private Jewelry jewelry;



//    @ManyToMany
//    @JoinTable(name = "account_auction",
//            joinColumns = @JoinColumn(name="account_id"),
//            inverseJoinColumns = @JoinColumn(name="auction_id"))
//    @JsonManagedReference
//    Set<Account> accounts;

    @ManyToOne
    @JoinColumn(name = "staff_id")

    private Account account;

    @OneToMany(mappedBy = "auction", cascade = CascadeType.ALL, fetch = FetchType.EAGER)//
    @JsonIgnore
    private Set<Bid> bid;

    @OneToOne(mappedBy = "auction", cascade = CascadeType.ALL)
    @JsonIgnore
    private Order order;
}
