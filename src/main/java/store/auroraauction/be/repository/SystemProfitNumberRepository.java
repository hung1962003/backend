package store.auroraauction.be.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import store.auroraauction.be.entity.SystemProfitNumber;
@Repository
public interface SystemProfitNumberRepository extends JpaRepository<SystemProfitNumber,Long> {
}
