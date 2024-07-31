package store.auroraauction.be.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class SystemProfitNumber {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    double percentofsystemprofit;
}
