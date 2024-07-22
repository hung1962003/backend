package store.auroraauction.be.Models;


import jakarta.persistence.Column;
import lombok.Data;
import store.auroraauction.be.entity.Category;

import java.util.Set;

@Data

public class SendToManagerRequest {
    String name;
    int low_estimated_price;
    int high_estimated_price;
    int weight;
    @Column(columnDefinition = "TEXT") // create a column in sql with data  type is TEXT
    String description;
    @Column(columnDefinition = "TEXT") // create a column in sql with data  type is TEXT
    String ConditionReport;
    String images;
    Category category;
}
