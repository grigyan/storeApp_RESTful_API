package grid.intern.storeApp.controller;

import grid.intern.storeApp.exceptions.customerExceptions.CustomerExistsException;
import grid.intern.storeApp.exceptions.customerExceptions.CustomerNotFoundException;
import grid.intern.storeApp.model.Customer;
import grid.intern.storeApp.repository.CustomerRepository;
import grid.intern.storeApp.repository.ProductRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
public class CustomerController {
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;
    private final PasswordEncoder passwordEncoder;

    public CustomerController(CustomerRepository customerRepository, ProductRepository productRepository) {
        this.customerRepository = customerRepository;
        this.productRepository = productRepository;
        passwordEncoder = new BCryptPasswordEncoder();
    }

    // getting all users
    @GetMapping("/customers")
    public List<Customer> all(HttpSession session) {
        if (session == null) {
            System.out.println("session is null");
        }

        System.out.println(session.getId());
        return customerRepository.findAll();
    }

    // getting user by id
    @GetMapping("/customers/{id}")
    public Customer one(@PathVariable Long id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException(id));
    }

    // registering new user
    @PostMapping("/customers/signup")
    public Customer signUp(@RequestBody Customer customer) {
        if (customerRepository.existsCustomerByEmail(customer.getEmail())) {
            throw new CustomerExistsException(customer.getEmail());
        }
        String hashedPassword = passwordEncoder.encode(customer.getPassword());
        customer.setPassword(hashedPassword);

        return customerRepository.save(customer);
    }

    // login existing user
    @PostMapping(value = "/customers/login",  produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Map<String, String> login(@RequestBody Customer customer, HttpSession session) {
        if (!customerRepository.existsCustomerByEmail(customer.getEmail())) {
            throw new CustomerNotFoundException(customer.getEmail());
        }
        Customer dbCustomer = customerRepository.getCustomerByEmail(customer.getEmail());

        if (passwordEncoder.matches(customer.getPassword(), dbCustomer.getPassword()) &&
        customer.getEmail().equals(dbCustomer.getEmail())) {
            return Collections.singletonMap("sessionId", session.getId());
        }

        return Collections.singletonMap("info", "wrong username or password");
    }

    // editing existing user
    @PutMapping("/customers/{id}")
    public Customer replaceCustomer(@PathVariable Long id, @RequestBody Customer newCustomer) {
        return customerRepository.findById(id)
                .map(customer -> {
                        customer.setEmail(newCustomer.getEmail());
                        customer.setPassword(passwordEncoder.encode(newCustomer.getPassword()));
                        return customerRepository.save(customer);
                })
                .orElseGet(() -> {
                        newCustomer.setId(id);
                        return customerRepository.save(newCustomer);
                });
    }
}
