package store.auroraauction.be.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import store.auroraauction.be.enums.TransactionEnum;

import java.util.Date;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "transaction")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    double amount;

    String createdTransaction;
    String accountNumber;
    String accountName;
    String bankName;
    String reasonWithdrawReject;

    @Enumerated(EnumType.STRING)
    TransactionEnum transactionEnum;

    @ManyToOne
    @JoinColumn(name = "wallet_id")
    @JsonIgnore
    private Wallet  wallet;

    @JsonIgnore
    @OneToOne(mappedBy = "transaction",cascade = CascadeType.ALL)
    private SystemProfit systemProfit;

}
