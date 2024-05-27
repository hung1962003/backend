package store.auroraauction.be.Models;

import lombok.Data;
import store.auroraauction.be.entity.Account;
@Data
public class AccountResponse extends Account {
    String token;
}
