package grid.intern.storeApp.controller;

import grid.intern.storeApp.exceptions.productExceptions.ProductNotFoundException;
import grid.intern.storeApp.model.Product;
import grid.intern.storeApp.repository.ProductRepository;
import grid.intern.storeApp.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService service) {
        this.productService = service;
    }

    // get all products
    @GetMapping("/")
    public ResponseEntity<List<Product>> all() {
        return new ResponseEntity<>(productService.findAll(), HttpStatus.OK);
    }

    // get product by id
    @GetMapping("/{id}")
    public ResponseEntity<Product> getById(@PathVariable Long id) {
        return new ResponseEntity<>(productService.findById(id), HttpStatus.OK);
    }
}
