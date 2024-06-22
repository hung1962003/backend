package store.auroraauction.be.Models;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import store.auroraauction.be.enums.TransactionEnum;
@Getter
@AllArgsConstructor
@Setter
@NoArgsConstructor

public class TransactionResponse {
    private long transactionId;
    private double amount;
    @Enumerated(EnumType.STRING)
    TransactionEnum transactionEnum;
    private  String createdTransaction;
}
