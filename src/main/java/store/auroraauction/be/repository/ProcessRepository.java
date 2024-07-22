package store.auroraauction.be.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import store.auroraauction.be.entity.Process;
import store.auroraauction.be.entity.RequestBuy;

import java.util.List;

@Repository
public interface ProcessRepository extends JpaRepository<Process ,Integer> {
    Process findProcessById(long id);
    List<Process> findByRequestBuy_Id(long id);
}
