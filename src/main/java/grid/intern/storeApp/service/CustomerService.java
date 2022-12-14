package grid.intern.storeApp.service;

import grid.intern.storeApp.exceptions.customerExceptions.CustomerNotFoundException;
import grid.intern.storeApp.model.dto.CustomerSessionDto;
import grid.intern.storeApp.model.entity.Customer;
import grid.intern.storeApp.repository.CustomerRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final PasswordEncoder passwordEncoder;

    public CustomerService(CustomerRepository customerRepository) {
        this.passwordEncoder = new BCryptPasswordEncoder();
        this.customerRepository = customerRepository;
    }

    public List<Customer> findAll() {
        return customerRepository.findAll();
    }

    public Customer findById(Integer id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException(id));
    }

    public boolean existsCustomerByEmail(String email) {
        return customerRepository.existsCustomerByEmail(email);
    }

    public Customer save(Customer customer) {
        return customerRepository.save(customer);
    }

    public Customer getCustomerByEmail(String email) {
        return customerRepository.getCustomerByEmail(email);
    }

    public boolean successfulLogIn(Customer customer, HttpSession session) {
        if (!this.existsCustomerByEmail(customer.getEmail())) {
            throw new CustomerNotFoundException(customer.getEmail());
        }

        Customer customerFromDb = this.getCustomerByEmail(customer.getEmail());
        if (passwordEncoder.matches(customer.getPassword(), customerFromDb.getPassword())) {
            CustomerSessionDto customerSessionDto = new CustomerSessionDto(customerFromDb.getId());
            session.setAttribute(customer.getEmail(), customerSessionDto);

            return true;
        }

        return false;
    }
}
