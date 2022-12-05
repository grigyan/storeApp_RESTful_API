package grid.intern.storeApp.controller;

import grid.intern.storeApp.exceptions.customerExceptions.CustomerExistsException;
import grid.intern.storeApp.exceptions.customerExceptions.CustomerNotFoundException;
import grid.intern.storeApp.model.Customer;
import grid.intern.storeApp.repository.CustomerRepository;
import grid.intern.storeApp.repository.ProductRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public List<Customer> all() {
        return customerRepository.findAll();
    }

    // geting user by id
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
    @PostMapping("/customers/login")
    public String login(@RequestBody Customer customer, HttpSession session) {
        if (!customerRepository.existsCustomerByEmail(customer.getEmail())) {
            throw new CustomerNotFoundException(customer.getEmail());
        }
        Customer dbCustomer = customerRepository.getCustomerByEmail(customer.getEmail());

        if (passwordEncoder.matches(customer.getPassword(), dbCustomer.getPassword()) &&
        customer.getEmail().equals(dbCustomer.getEmail())) {
            return session.getId();
        }

        return "failed to login";
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
