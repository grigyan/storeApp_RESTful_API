package grid.intern.storeApp.repository;

import grid.intern.storeApp.model.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {
    boolean existsCustomerByEmail(String email);
    Customer getCustomerByEmail(String email);
}