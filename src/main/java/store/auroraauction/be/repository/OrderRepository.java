package store.auroraauction.be.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;
import store.auroraauction.be.entity.Order;
@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

}
