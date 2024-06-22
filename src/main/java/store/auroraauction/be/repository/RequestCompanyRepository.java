package store.auroraauction.be.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import store.auroraauction.be.entity.RequestCompany;
@Repository


public interface RequestCompanyRepository extends JpaRepository<RequestCompany,Long> {
    RequestCompany findRequestCompanyById(Long id);
}
