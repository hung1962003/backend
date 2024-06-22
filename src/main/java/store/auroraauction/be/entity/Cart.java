package store.auroraauction.be.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @OneToOne(mappedBy ="cart")
    private Account account;


    @ManyToMany(mappedBy ="carts",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    Set<Jewelry> jewelries;
}
