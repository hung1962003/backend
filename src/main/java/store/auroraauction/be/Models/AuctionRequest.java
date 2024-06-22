package store.auroraauction.be.Models;

import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;


import java.util.Date;
import java.util.List;
import java.util.Set;

@Getter
@Setter
public class AuctionRequest {
    String name;
    @Column(columnDefinition = "TEXT") // create a column in sql with data  type is TEXT
    String description;
    Date start_date;
    Date end_date;
    String title;
    String image;

}
