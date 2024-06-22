package store.auroraauction.be.utils;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import store.auroraauction.be.entity.Account;

@Component
public class AccountUtils {
    public Account getCurrentAccount() {
        return (Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    }
}
