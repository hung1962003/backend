package store.auroraauction.be.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import store.auroraauction.be.entity.SystemProfit;

import java.util.List;
@Repository
public interface SystemProfitRepository extends JpaRepository<SystemProfit,Long> {

    @Query( "SELECT SUM(sp.balance) FROM SystemProfit sp" )
    float getTotalProfit();

        @Query(value ="SELECT SUM(balance) FROM `aurora-auction`.system_profit  WHERE EXTRACT(MONTH FROM system_profit.date) = :month  AND EXTRACT(YEAR FROM system_profit.date) = :year" ,nativeQuery = true)
    float getProfitByMonth(int month, int year);

    @Query(value ="SELECT * FROM `aurora-auction`.system_profit  WHERE EXTRACT(MONTH FROM system_profit.date) = :month  AND EXTRACT(YEAR FROM system_profit.date) = :year" ,nativeQuery = true)
    List<SystemProfit> getAllHistorySystemProfit(int month, int year);

}
