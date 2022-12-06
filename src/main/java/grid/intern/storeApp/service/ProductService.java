package grid.intern.storeApp.service;

import grid.intern.storeApp.exceptions.productExceptions.ProductNotFoundException;
import grid.intern.storeApp.model.Product;
import grid.intern.storeApp.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public Product findById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));
    }


}
