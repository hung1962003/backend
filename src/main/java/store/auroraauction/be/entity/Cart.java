package store.auroraauction.be.entity;

import jakarta.persistence.Entity;
import lombok.*;


@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Cart {

    private int id;
    //private int AccountId;
    private int JewelryId;
    private int Quantity;
}
