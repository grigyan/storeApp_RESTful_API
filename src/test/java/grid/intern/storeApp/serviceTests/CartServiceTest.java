package grid.intern.storeApp.serviceTests;

import grid.intern.storeApp.model.entity.Cart;
import grid.intern.storeApp.model.entity.Customer;
import grid.intern.storeApp.repository.CartRepository;
import grid.intern.storeApp.service.CartService;
import grid.intern.storeApp.service.CustomerService;
import grid.intern.storeApp.service.ProductService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CartServiceTest {

    @Mock
    CartRepository cartRepository;

    @InjectMocks
    CartService cartService;

    @Test
    public void shouldFindAllCartItemsByCustomerId() {
        // given
        Customer customer = new Customer();
        customer.setId(29);

        Cart cartItem = new Cart();
        cartItem.setId(1);
        cartItem.setQuantity(2);
        cartItem.setCustomer(customer);

        List<Cart> cartItems = List.of(cartItem);

        // when
        when(cartRepository.findAllByCustomerId(29)).thenReturn(cartItems);
        List<Cart> actual = cartService.findAllByCustomerId(29);

        // then
        assertEquals(actual.size(), 1);
        assertEquals(actual.get(0).getCustomer().getId(), 29);
        assertEquals(actual.get(0).getQuantity(), 2);
    }
}
