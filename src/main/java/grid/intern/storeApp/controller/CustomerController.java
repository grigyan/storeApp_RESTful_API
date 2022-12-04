package grid.intern.storeApp.controller;

import grid.intern.storeApp.exceptions.customerExceptions.CustomerExistsException;
import grid.intern.storeApp.exceptions.customerExceptions.CustomerNotFoundException;
import grid.intern.storeApp.model.Customer;
import grid.intern.storeApp.repository.CustomerRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CustomerController {
    private final CustomerRepository repository;
    private final PasswordEncoder passwordEncoder;

    public CustomerController(CustomerRepository repository) {
        this.repository = repository;
        passwordEncoder = new BCryptPasswordEncoder();
    }

    // getting all users
    @GetMapping("/customers")
    public List<Customer> all() {
        return repository.findAll();
    }

    // geting user by id
    @GetMapping("/customers/{id}")
    public Customer one(@PathVariable Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException(id));
    }

    // registering user
    @PostMapping("/customers/signup")
    public Customer signUp(@RequestBody Customer customer) {
        if (repository.existsCustomerByEmail(customer.getEmail())) {
            throw new CustomerExistsException(customer.getEmail());
        }
        String hashedPassword = passwordEncoder.encode(customer.getPassword());
        customer.setPassword(hashedPassword);

        return repository.save(customer);
    }

    // login user
    @PostMapping("/customers/login")
    public String login(@RequestBody Customer customer) {
        if (!repository.existsCustomerByEmail(customer.getEmail())) {
            throw new CustomerNotFoundException(customer.getEmail());
        }
        Customer dbCustomer = repository.getCustomerByEmail(customer.getEmail());

        if (passwordEncoder.matches(customer.getPassword(), dbCustomer.getPassword()) &&
        customer.getEmail().equals(dbCustomer.getEmail())) {
            return "success";
        }

        return "fail";
    }

    // editing user
    @PutMapping("/customers/{id}")
    public Customer replaceCustomer(@PathVariable Long id, @RequestBody Customer newCustomer) {
        return repository.findById(id)
                .map(customer -> {
                        customer.setEmail(newCustomer.getEmail());
                        customer.setPassword(passwordEncoder.encode(newCustomer.getPassword()));
                        return repository.save(customer);
                })
                .orElseGet(() -> {
                        newCustomer.setId(id);
                        return repository.save(newCustomer);
                });
    }
}
