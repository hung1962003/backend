package store.auroraauction.be.Models;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RequestBuyRequest {
    @Column(columnDefinition = "TEXT") // create a column in sql with data  type is TEXT
    private String description;
    private String image_url;
    private String title;
    private int category_id;
    private double weight;
    private String material;
    private String name;


}
