package grid.intern.storeApp.controller;

import grid.intern.storeApp.exceptions.customerExceptions.CustomerExistsException;
import grid.intern.storeApp.exceptions.customerExceptions.CustomerNotFoundException;
import grid.intern.storeApp.model.entity.Customer;
import grid.intern.storeApp.repository.ProductRepository;
import grid.intern.storeApp.service.CustomerService;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/customers")
public class CustomerController {
    private final CustomerService customerService;
    private final ProductRepository productRepository;
    private final PasswordEncoder passwordEncoder;

    public CustomerController(CustomerService service, ProductRepository productRepository) {
        this.productRepository = productRepository;
        this.customerService = service;
        passwordEncoder = new BCryptPasswordEncoder();
    }

    @GetMapping("/")
    public ResponseEntity<List<Customer>> getCustomers() {
        return new ResponseEntity<>(customerService.findAll(), HttpStatus.OK);
    }

    // getting user by id
    @GetMapping("/{id}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable Long id) {
        return new ResponseEntity<>(customerService.findById(id), HttpStatus.OK);
    }

    // registering new user
    @PostMapping("/signup")
    public ResponseEntity<Customer> signUp(@RequestBody Customer customer) {
        if (customerService.existsCustomerByEmail(customer.getEmail())) {
            throw new CustomerExistsException(customer.getEmail());
        }
        String hashedPassword = passwordEncoder.encode(customer.getPassword());
        customer.setPassword(hashedPassword);

        return new ResponseEntity<>(customerService.save(customer), HttpStatus.OK);
    }

    // login existing user
    @PostMapping(value = "/login",  produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, String>> login(@RequestBody Customer customer, HttpSession session) {
        if (!customerService.existsCustomerByEmail(customer.getEmail())) {
            throw new CustomerNotFoundException(customer.getEmail());
        }

        Customer customerFromDb = customerService.getCustomerByEmail(customer.getEmail());
        if (passwordEncoder.matches(customer.getPassword(), customerFromDb.getPassword()) &&
        customer.getEmail().equals(customerFromDb.getEmail())) {
            return new ResponseEntity<>(Collections.singletonMap("sessionId", session.getId()),
                    HttpStatus.OK);
        }

        return new ResponseEntity<>(Collections.singletonMap("info", "wrong username or password"),
                HttpStatus.UNAUTHORIZED);
    }
}
