package grid.intern.storeApp.controllerTests;

import grid.intern.storeApp.controller.CheckoutController;
import grid.intern.storeApp.model.dto.CustomerSessionDto;
import grid.intern.storeApp.service.CartService;
import grid.intern.storeApp.service.CheckoutService;
import grid.intern.storeApp.service.CustomerService;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Map;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(controllers = CheckoutController.class)
@AutoConfigureMockMvc(addFilters = false)   // disabling security for tests
public class CheckoutControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CheckoutService checkoutService;

    @MockBean
    private CustomerService customerService;

    @MockBean
    private CartService cartService;

    @Test
    public void testCheckout_whenCustomerNotLoggedIn_thenStatusShouldBeUnauthorized() throws Exception {
        mockMvc.perform(get("/checkout/"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void testCheckout_whenCustomerIsLoggedIn_shouldReturnSuccessResponse() throws Exception {
        // given
        MockHttpSession session = new MockHttpSession();
        CustomerSessionDto customerSessionDto = new CustomerSessionDto(1);
        session.setAttribute("user", customerSessionDto);
        when(cartService.findAllByCustomerId(customerSessionDto.getCustomerId()))
                .thenReturn(new ArrayList<>());

        // when and then
        mockMvc.perform(get("/checkout/")
                        .session(session)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success", is(true)))
                .andExpect(jsonPath("$.message", is("Checkout completed.")));
    }


}
