package grid.intern.storeApp.serviceTests;

import grid.intern.storeApp.model.entity.Customer;
import grid.intern.storeApp.repository.CustomerRepository;
import grid.intern.storeApp.service.CustomerService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpSession;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceTest {
    @Mock
    CustomerRepository customerRepository;

    @InjectMocks
    CustomerService customerService;

    @Test
    public void shouldFindAllCustomers() {
        // given
        Customer customer = new Customer("customer@email.com", "pass1234");
        List<Customer> customers = List.of(customer);

        // when
        when(customerRepository.findAll()).thenReturn(customers);
        List<Customer> actual = customerService.findAll();

        // then
        assertEquals(actual.size(), 1);
        assertEquals(actual.get(0).getEmail(), "customer@email.com");
        assertEquals(actual.get(0).getPassword(), "pass1234");
    }

    @Test
    public void shouldFindCustomerById() {
        // given
        Customer customer = new Customer("customer@email.com", "pass1234");
        customer.setId(1);
        // when
        when(customerRepository.findById(1)).thenReturn(Optional.of(customer));
        Customer actual = customerService.findById(1);

        // then
        assertEquals(actual.getId(), customer.getId());
    }

    @Test
    public void shouldFindIfCustomerExists() {
        // when
        when(customerRepository.existsCustomerByEmail("customer@email.com")).thenReturn(true);
        when(customerRepository.existsCustomerByEmail("falseEmail@email.com")).thenReturn(false);

        boolean expectedTrue = customerService.isCustomerExistsByEmail("customer@email.com");
        boolean expectedFalse = customerService.isCustomerExistsByEmail("falseEmail@email.com");

        // then
        assertTrue(expectedTrue);
        assertFalse(expectedFalse);
    }

    @Test
    public void shouldReturnSavedCustomer() {
        // given
        Customer expected = new Customer("customer@email.com", "pass1234");

        // when
        when(customerRepository.save(any(Customer.class))).thenReturn(expected);
        Customer actual = customerService.save(expected);

        // then
        assertEquals(expected.getEmail(), actual.getEmail());
        assertEquals(expected.getPassword(), actual.getPassword());
    }

    @Test
    public void shouldFindCustomerByEmail() {
        //given
        Customer customer = new Customer("customer@email.com", "pass1234");

        // when
        when(customerRepository.getCustomerByEmail("customer@email.com")).thenReturn(customer);
        Customer actual = customerService.getCustomerByEmail("customer@email.com");

        // then
        assertEquals(customer.getEmail(), actual.getEmail());
        assertEquals(customer.getPassword(), actual.getPassword());
    }

    @Test
    public void shouldLogIn() {
        // given
        Customer customer = new Customer("customer@email.com", "1234");
        Customer customerFromDb = new Customer("customer@email.com", "$2a$10$szYq1iGDAKZnV6NRt956quLA9dmj31cd/EGKiA3K1zIvx.bgC4V1m");

        // when
        when(customerRepository.existsCustomerByEmail("customer@email.com")).thenReturn(true);
        when(customerRepository.getCustomerByEmail("customer@email.com")).thenReturn(customerFromDb);
        boolean isLoggedIn = customerService.isLoginSuccessful(customer, new MockHttpSession());

        // then
        assertTrue(isLoggedIn);
    }

}