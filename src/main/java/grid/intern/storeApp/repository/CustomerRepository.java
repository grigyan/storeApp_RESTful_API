package grid.intern.storeApp.repository;

import grid.intern.storeApp.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    boolean existsCustomerByEmail(String email);
    Customer getCustomerByEmail(String email);
}