package grid.intern.storeApp.repository;

import grid.intern.storeApp.model.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<Cart, Integer> {
    public List<Cart> findAllByCustomerId(Integer customerId);
    public Cart findAllByCustomerIdAndProductId(Integer customerId, Integer productId);
}
