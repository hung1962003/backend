package store.auroraauction.be.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Jewelry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int jewelryId;
    @Column(columnDefinition = "TEXT") // create a column in sql with data  type is TEXT
    String description;
    int last_price;
    int low_estimated_price;
    int high_estimated_price;
    //int bid_id;
    String image;
//   int category_id;
    String title;
   // int sell_id;
    boolean buy_status;
    boolean sell_status;
    @Column(name = "jewelry_condition")
    boolean jewelry_condition;
    @Column(columnDefinition = "TEXT") // create a column in sql with data  type is TEXT
    String ConditionReport;
    //int buy_id;
}
