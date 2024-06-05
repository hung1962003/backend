package store.auroraauction.be.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.sql.Time;
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
    Date end_date;
    Time start_time;
    String title;
    String image;

    @OneToMany(mappedBy = "auction", cascade = CascadeType.ALL)
    private List<Bill> bills;

    @ManyToMany(mappedBy ="auctions",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
            @JsonBackReference
    Set<Account> accounts;

    @OneToMany(mappedBy = "auction", cascade = CascadeType.ALL)
    private List<Bid> bid;


}
