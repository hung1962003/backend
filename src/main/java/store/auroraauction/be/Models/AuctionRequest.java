package store.auroraauction.be.Models;

import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;


import java.time.LocalDateTime;


@Getter
@Setter
public class AuctionRequest {
    String name;
    @Column(columnDefinition = "TEXT") // create a column in sql with data  type is TEXT
    String description;
    LocalDateTime start_date;
    LocalDateTime end_date;
    String title;
    String image;
    Long Staff_id;
    Long Jewelry_id;
}
