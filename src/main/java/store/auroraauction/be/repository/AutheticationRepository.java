package store.auroraauction.be.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import store.auroraauction.be.entity.Account;
import store.auroraauction.be.enums.RoleEnum;

import java.util.List;
import java.util.Set;

public interface AutheticationRepository extends JpaRepository<Account, Long> {
    // luu tru vo database
   //List<Account> findAccount();
    List<Account> findAccountByRoleEnum(RoleEnum roleEnum);
    Account findAccountByUsername(String username);
    Account getAccountById(long id);
    Account findByEmail(String email);
    Account findAccountByFirstname(String Firstname);

    @Query(value="SELECT COUNT(*) FROM account a WHERE a.role_enum = :role",nativeQuery = true)
    int countByRole(@Param("role") String role);
//    @Query(value = "SELECT a.* FROM account a " +
//            "JOIN account_auction aa ON a.id = aa.account_id " +
//            "WHERE aa.auction_id = :auctionId", nativeQuery = true)
//    Set<Account> findAccountByAuction_id(@Param("auctionId")Long auctionId);
}
