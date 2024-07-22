package store.auroraauction.be.Models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import store.auroraauction.be.entity.Transaction;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ListSystemProfitMapByDTO {
    Long id;
    double balance;
    String description;
    LocalDateTime date;
    Transaction transaction;
}
