package grid.intern.storeApp.service;

import grid.intern.storeApp.exceptions.productExceptions.ProductNotFoundException;
import grid.intern.storeApp.model.dto.AddToCartDto;
import grid.intern.storeApp.model.entity.Cart;
import grid.intern.storeApp.model.entity.Customer;
import grid.intern.storeApp.model.entity.Product;
import grid.intern.storeApp.repository.CartRepository;
import grid.intern.storeApp.repository.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class CartService {
    private final CartRepository cartRepository;
    private final ProductService productService;

    public CartService(CartRepository cartRepository, ProductService productService) {
        this.productService = productService;
        this.cartRepository = cartRepository;
    }

    public void addToCart(AddToCartDto addToCartDto, Customer customer) {
        Product product = productService.findById(addToCartDto.getProductId());

        Cart cart = new Cart();
        cart.setProduct(product);
        cart.setCustomer(customer);
        cart.setQuantity(addToCartDto.getQuantity());

        cartRepository.save(cart);

        // add case when the product is not found
        // add case when the quantity is wrong
    }




}
