package store.auroraauction.be.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import store.auroraauction.be.entity.Jewelry;
import store.auroraauction.be.enums.StatusJewelryEnum;

import java.util.List;


public interface JewelryRepository extends JpaRepository<Jewelry, Long> {
    List<Jewelry> findJewelryByCategory_Id(Long id);
    List<Jewelry> findJewelryByStatusJewelryEnum(StatusJewelryEnum statusJewelryEnum);

    Jewelry findById(long id);

    @Query(value="select count(*) from jewelry j where status_jewelry_enum= :statusJewelry", nativeQuery = true)
    int countJewelryIsSold(@Param(value="statusJewelry") String status);
    @Query(value = "select count(*) from jewelry j", nativeQuery = true)
    int countAllJewelries();
    List<Jewelry> findJewelryByNameContaining(String name);
}
