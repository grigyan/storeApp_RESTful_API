package grid.intern.storeApp.controllerTests;

import grid.intern.storeApp.controller.CustomerController;
import grid.intern.storeApp.model.entity.Customer;
import grid.intern.storeApp.service.CustomerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(controllers = CustomerController.class)
@AutoConfigureMockMvc(addFilters = false)   // disabling security for tests
public class CustomerControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CustomerService customerService;

    @Test
    public void testGetAllCustomers() throws Exception {
        // given
        Customer customer1 = new Customer("cust1@email.com", "pass1");
        Customer customer2 = new Customer("222customer@email.com", "cast2");

        // when
        when(customerService.findAll()).thenReturn(List.of(customer1, customer2));

        // then
        mockMvc.perform(get("/customer/"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].email", is("cust1@email.com")))
                .andExpect(jsonPath("$[0].password", is("pass1")))
                .andExpect(jsonPath("$[1].email", is("222customer@email.com")))
                .andExpect(jsonPath("$[1].password", is("cast2")));
    }

    @Test
    public void testGetCustomerById() throws Exception {
        // given
        Customer customer = new Customer("customer@email", "customerPass");
        customer.setId(1);
        // when
        when(customerService.findById(1)).thenReturn(customer);
        // then
        mockMvc.perform(get("/customer/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email", is("customer@email")))
                .andExpect(jsonPath("$.password", is("customerPass")))
                .andExpect(jsonPath("$.id", is(1)));
    }

    @Test
    public void testSignUp_whenCustomerEmailAlreadyExists_shouldReturnConflict409() throws Exception {
        // given and when
        when(customerService.isCustomerExistsByEmail(anyString())).thenReturn(true);

        // then
        mockMvc.perform(post("/customer/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"email\": \"admin@gmail.com\", \"password\": \"1234\" }"))
                .andExpect(status().isConflict());
    }

    @Test
    public void testSignUp_whenCustomerEmailIsAlright_shouldReturnOk() throws Exception {
        // given and when
        when(customerService.isCustomerExistsByEmail(anyString())).thenReturn(false);

        // then
        mockMvc.perform(post("/customer/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"email\": \"admin@gmail.com\", \"password\": \"1234\" }"))
                .andExpect(status().isOk());
    }

    @Test
    public void testLogIn_whenCustomerLoginCredentialsAreWrong_shouldReturnUnauthorized() throws Exception {
        // given and when
        when(customerService.isLoginSuccessful(any(), any())).thenReturn(false);

        // then
        mockMvc.perform(post("/customer/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{ \"email\": \"admin@gmail.com\", \"password\": \"1234\" }"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void testLogIn_whenCustomerLoginCredentialsAreRight_shouldReturnOk() throws Exception {
        // given and when
        when(customerService.isLoginSuccessful(any(), any())).thenReturn(true);

        // then
        mockMvc.perform(post("/customer/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"email\": \"admin@gmail.com\", \"password\": \"1234\" }"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.sessionId").exists());
    }
}