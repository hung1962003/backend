package store.auroraauction.be.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;


@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name="cart")
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="account-id",referencedColumnName = "id")
    private Account account;
    private int Quantity;

    @ManyToMany(mappedBy ="carts",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JsonBackReference
    Set<Jewelry> jewelries;
}
