package store.auroraauction.be.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import store.auroraauction.be.entity.Account;

public interface AutheticationRepository extends JpaRepository<Account, Long> {
    // luu tru vo database
   //List<Account> findAccount();
    Account findAccountByUsername(String username);
    Account getAccountById(long id);
    Account findByEmail(String email);
    Account findAccountByFirstname(String Firstname);
}
