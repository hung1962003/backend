package store.auroraauction.be.Models;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.util.Set;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class JewelryResponse {
    Long id;
    String name;
    int low_estimated_price;
    int high_estimated_price;
    int weight;
    @Column(columnDefinition = "TEXT") // create a column in sql with data  type is TEXT
    String description;
    @Column(columnDefinition = "TEXT") // create a column in sql with data  type is TEXT
    String ConditionReport;
    int category_id;
    String image_url;
}
