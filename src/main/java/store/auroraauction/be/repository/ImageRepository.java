package store.auroraauction.be.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import store.auroraauction.be.entity.Image;

import java.util.Set;

@Repository
public interface ImageRepository extends JpaRepository<Image,Long> {
    Set<Image> findImageByJewelry_Id(Long id);
}
