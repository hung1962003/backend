package store.auroraauction.be.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import store.auroraauction.be.entity.Cart;
@Repository
public interface CartRepository extends JpaRepository<Cart,Long> {
    Cart findCartByAccountId(Long accountId);

}
