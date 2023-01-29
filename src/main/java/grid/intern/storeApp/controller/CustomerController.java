package grid.intern.storeApp.controller;

import grid.intern.storeApp.exceptions.customerExceptions.CustomerExistsException;
import grid.intern.storeApp.model.entity.Customer;
import grid.intern.storeApp.service.CustomerService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/customer")
public class CustomerController {
    private final CustomerService customerService;
    private final PasswordEncoder passwordEncoder;

    public CustomerController(CustomerService service) {
        this.customerService = service;
        passwordEncoder = new BCryptPasswordEncoder();
    }

    @GetMapping("/")
    public ResponseEntity<List<Customer>> getCustomers() {
        return new ResponseEntity<>(customerService.findAll(), HttpStatus.OK);
    }

    // getting user by id
    @GetMapping("/{id}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable Integer id) {
        return new ResponseEntity<>(customerService.findById(id), HttpStatus.OK);
    }

    // registering new user
    @PostMapping("/signup")
    public ResponseEntity<Customer> signUp(@RequestBody Customer customer) {
        if (customerService.isCustomerExistsByEmail(customer.getEmail())) {
            throw new CustomerExistsException(customer.getEmail());
        }
        String hashedPassword = passwordEncoder.encode(customer.getPassword());
        customer.setPassword(hashedPassword);

        return new ResponseEntity<>(customerService.save(customer), HttpStatus.OK);
    }

    // login existing user
    @PostMapping(value = "/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody Customer customer, HttpServletRequest request) {
        if (customerService.isLoginSuccessful(customer, request.getSession(true))) {
            return new ResponseEntity<>(Collections.singletonMap("sessionId", request.getSession(false).getId()),
                    HttpStatus.OK);
        } else {
            return new ResponseEntity<>(Collections.singletonMap("info", "wrong username or password"),
                    HttpStatus.UNAUTHORIZED);
        }
    }
}
