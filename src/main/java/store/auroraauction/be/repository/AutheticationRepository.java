package store.auroraauction.be.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import store.auroraauction.be.entity.Account;

import java.util.List;

public interface AutheticationRepository extends JpaRepository<Account, Long> {
    // luu tru vo database
   //List<Account> findAccount();
    Account findAccountByPhoneNumber(String phone);

}
