package grid.intern.storeApp.controller;

import grid.intern.storeApp.model.entity.Product;
import grid.intern.storeApp.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
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
    public ResponseEntity<Product> getById(@PathVariable Integer id) {
        return new ResponseEntity<>(productService.findById(id), HttpStatus.OK);
    }
}
