package store.auroraauction.be.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.format.annotation.DateTimeFormat;

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

    Date start_date;
//    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd")
    Date end_date;
    String title;
    String image;


    @OneToMany(mappedBy = "auction", cascade = CascadeType.ALL)
    private List<Order> orders;

    @ManyToMany
    @JoinTable(name = "account_auction",
            joinColumns = @JoinColumn(name="account_id"),
            inverseJoinColumns = @JoinColumn(name="auction_id"))
    @JsonManagedReference
    Set<Account> accounts;

    @OneToMany(mappedBy = "auction", cascade = CascadeType.ALL)
    private List<Bid> bid;

    @ManyToMany
    @JoinTable(name = "jewelry_auction",
            joinColumns = @JoinColumn(name="jewelry_id"),
            inverseJoinColumns = @JoinColumn(name="auction_id"))
    @JsonManagedReference
    Set<Jewelry> jewelries;

}
