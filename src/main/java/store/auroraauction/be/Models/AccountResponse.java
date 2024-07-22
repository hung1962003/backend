package store.auroraauction.be.Models;

import lombok.Getter;
import lombok.Setter;
import store.auroraauction.be.entity.Account;

@Getter
@Setter
public class AccountResponse extends Account {
    private String token;

}
