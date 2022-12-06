package grid.intern.storeApp.service;

import grid.intern.storeApp.model.dto.AddToCartDto;
import grid.intern.storeApp.model.entity.Cart;
import grid.intern.storeApp.repository.CartRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class CartService {
    private final CartRepository cartRepository;

    public CartService(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }



    public Cart addToCartFromDto(AddToCartDto addToCartDto, int customerId) {
        return null;
        //Cart cart = new Cart(addToCartDto, customerId);
        //return cart;
    }


}
