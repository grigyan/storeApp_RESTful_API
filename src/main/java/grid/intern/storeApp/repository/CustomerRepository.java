package grid.intern.storeApp.repository;

import grid.intern.storeApp.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    boolean existsCustomerByEmail(String model);
    Customer getCustomerByEmail(String email);
}