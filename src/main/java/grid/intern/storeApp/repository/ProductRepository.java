package grid.intern.storeApp.repository;

import grid.intern.storeApp.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {}
