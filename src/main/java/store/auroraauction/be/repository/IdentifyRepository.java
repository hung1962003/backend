package store.auroraauction.be.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import store.auroraauction.be.entity.Identify;

@Repository
public interface IdentifyRepository extends JpaRepository<Identify,Long> {
}
