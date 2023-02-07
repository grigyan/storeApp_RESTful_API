package grid.intern.storeApp.service;

import grid.intern.storeApp.exceptions.cartExceptions.LowInStockException;
import grid.intern.storeApp.model.dto.CustomerSessionDto;
import grid.intern.storeApp.model.entity.Cart;
import grid.intern.storeApp.model.entity.Product;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CheckoutService {
    private final CustomerService customerService;
    private final CartService cartService;

    public CheckoutService(CustomerService customerService, CartService cartService) {
        this.customerService = customerService;
        this.cartService = cartService;
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void checkoutRequest(CustomerSessionDto customerSessionDto) {
        Integer customerId = customerSessionDto.getCustomerId();
        List<Cart> customerItems = cartService.findAllByCustomerId(customerId);
        for (Cart item : customerItems) {
            Product product = item.getProduct();
            if (item.getQuantity() > product.getAvailable()) {
                throw new LowInStockException(product.getAvailable());
            }
            product.setAvailable(product.getAvailable() - item.getQuantity());

            cartService.deleteCartItem(item.getId(), customerService.findById(customerSessionDto.getCustomerId()));
        }
    }


}