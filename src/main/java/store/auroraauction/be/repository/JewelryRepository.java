package store.auroraauction.be.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import store.auroraauction.be.entity.Jewelry;

import java.util.List;

@Repository
public interface JewelryRepository extends JpaRepository<Jewelry, Long> {
    List<Jewelry> findJewelryByCategory_Id(Long id);
}
