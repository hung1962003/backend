package store.auroraauction.be.Models;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class JewelryRequest {
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
    String material;
}
