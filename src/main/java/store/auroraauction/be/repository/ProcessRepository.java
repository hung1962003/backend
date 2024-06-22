package store.auroraauction.be.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import store.auroraauction.be.entity.Process;
@Repository
public interface ProcessRepository extends JpaRepository<Process ,Integer> {
    Process findProcessById(int id);
}
