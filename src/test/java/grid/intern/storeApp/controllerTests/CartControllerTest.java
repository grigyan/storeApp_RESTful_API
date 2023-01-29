package grid.intern.storeApp.controllerTests;

import grid.intern.storeApp.controller.CartController;
import grid.intern.storeApp.model.dto.CartDto;
import grid.intern.storeApp.model.dto.CartItemDto;
import grid.intern.storeApp.model.dto.CustomerSessionDto;
import grid.intern.storeApp.model.entity.Customer;
import grid.intern.storeApp.model.entity.Product;
import grid.intern.storeApp.service.CartService;
import grid.intern.storeApp.service.CustomerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(controllers = CartController.class)
@AutoConfigureMockMvc(addFilters = false)   // disabling security for tests
public class CartControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    CartService cartService;

    @MockBean
    CustomerService customerService;

    @Test
    public void testAddToCart_userNotLoggedIt_shouldReturnUnauthenticated() throws Exception {
        mockMvc.perform(post("/cart/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"productId\" : 2364, \"quantity\" : 3 }"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void testAddToCart_userLoggedIn_shouldReturnOk() throws Exception {
        // given
        CustomerSessionDto customerSessionDto = new CustomerSessionDto(1);
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("user", customerSessionDto);
        Customer customer = new Customer("customer@mail", "pass");

        // when
        when(customerService.findById(1)).thenReturn(customer);

        // then
        mockMvc.perform(post("/cart/")
                        .session(session)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"productId\" : 2364, \"quantity\" : 3 }"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.message", is("Added to Cart")))
                .andExpect(jsonPath("$.success", is(true)));
    }

    @Test
    public void testGetCartItems_userNotLoggedIn_shouldReturnUnauthorized() throws Exception {
        mockMvc.perform(get("/cart/"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void testGetCartItems_userLoggedIn_shouldReturnCartContent() throws Exception {
        // given
        CustomerSessionDto customerSessionDto = new CustomerSessionDto(23);
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("user", customerSessionDto);

        CartItemDto cartItemDto1 = new CartItemDto();
        cartItemDto1.setId(1);
        cartItemDto1.setProduct(new Product(287, "Computer", 3, 405.4));
        cartItemDto1.setQuantity(2);
        List<CartItemDto> cartItemDtos = List.of(cartItemDto1);

        CartDto cartDto = new CartDto();
        cartDto.setCartItemList(cartItemDtos);
        cartDto.setTotalCost(810.8);

        // when
        when(cartService.getCartInfo(23)).thenReturn(cartDto);

        // then
        mockMvc.perform(get("/cart/")
                        .session(session))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.cartItemList[0].id", is(1)))
                .andExpect(jsonPath("$.cartItemList[0].quantity", is(2)))
                .andExpect(jsonPath("$.cartItemList[0].product.id", is(287)))
                .andExpect(jsonPath("$.cartItemList[0].product.title", is("Computer")))
                .andExpect(jsonPath("$.cartItemList[0].product.available", is(3)))
                .andExpect(jsonPath("$.cartItemList[0].product.price", is(405.4)))
                .andExpect(jsonPath("$.totalCost", is(810.8)));
    }

    @Test
    public void testDeleteCartItem_userNotLoggedIn_shouldBeUnauthorized() throws Exception {
        mockMvc.perform(delete("/cart/1"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void testDeleteCartItem_userLoggedIn_ShouldBeOk() throws Exception {
        // given
        CustomerSessionDto customerSessionDto = new CustomerSessionDto(1);
        MockHttpSession mockHttpSession = new MockHttpSession();
        mockHttpSession.setAttribute("user", customerSessionDto);
        Customer customer = new Customer("customer@email.com", "pass");

        // when
        when(customerService.findById(1)).thenReturn(customer);
        // then
        mockMvc.perform(delete("/cart/1")
                        .session(mockHttpSession))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message", is("Item has been removed")))
                .andExpect(jsonPath("$.success", is(true)));
    }

    @Test
    public void testModifyCartItem_userNotLoggedIn_shouldBeUnauthenticated() throws Exception {
        mockMvc.perform(put("/cart/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"productId\" : 2364, \"productQuantity\" : 4 }"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void testModifyCartItem_userLoggedIn_shouldBeOk() throws Exception {
        // given
        CustomerSessionDto customerSessionDto = new CustomerSessionDto(1);
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("user", customerSessionDto);

        // when and then
        mockMvc.perform(put("/cart/")
                        .session(session)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"productId\" : 2364, \"productQuantity\" : 4 }"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success", is(true)))
                .andExpect(jsonPath("$.message", is("Item was modified")));
    }


}