package grid.intern.storeApp.service;

import grid.intern.storeApp.exceptions.cartExceptions.ItemDoesNotBelongToUserException;
import grid.intern.storeApp.exceptions.cartExceptions.ItemNotFoundException;
import grid.intern.storeApp.exceptions.cartExceptions.LowInStockException;
import grid.intern.storeApp.model.dto.AddToCartDto;
import grid.intern.storeApp.model.dto.CartDto;
import grid.intern.storeApp.model.dto.CartItemDto;
import grid.intern.storeApp.model.entity.Cart;
import grid.intern.storeApp.model.entity.Customer;
import grid.intern.storeApp.model.entity.Product;
import grid.intern.storeApp.repository.CartRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CartService {
    private final CartRepository cartRepository;
    private final ProductService productService;

    public CartService(CartRepository cartRepository, ProductService productService) {
        this.productService = productService;
        this.cartRepository = cartRepository;
    }

    public void addToCart(AddToCartDto addToCartDto, Customer customer) {
        Product product = productService.findById(addToCartDto.getProductId());
        if (product == null) {
            throw new ItemNotFoundException(addToCartDto.getProductId());
        }

        if (addToCartDto.getQuantity() > product.getAvailable()) {
            throw new LowInStockException(product.getAvailable());
        }

        Cart cart = new Cart();
        cart.setProduct(product);
        cart.setCustomer(customer);
        cart.setQuantity(addToCartDto.getQuantity());

        cartRepository.save(cart);

    }

    public List<Cart> findAllByCustomerId(Integer customerId) {
        return cartRepository.findAllByCustomerId(customerId);
    }

    public CartDto listAllItems(Customer customer) {
        List<Cart> cartList = cartRepository.findAllByCustomerId(customer.getId());

        List<CartItemDto> cartItems = new ArrayList<>();
        double total = 0;

        for (Cart cart : cartList) {
            CartItemDto cartItemDto = new CartItemDto(cart);
            total += cartItemDto.getQuantity() * cartItemDto.getProduct().getPrice();
            cartItems.add(cartItemDto);
        }

        CartDto cartDto = new CartDto();
        cartDto.setCartItemList(cartItems);
        cartDto.setTotalCost(total);
        return cartDto;
    }

    public void deleteCartItem(Integer itemId, Customer customer) {
        Cart cart = cartRepository.findById(itemId)
                .orElseThrow(() -> new ItemNotFoundException(itemId));

        if (!cart.getCustomer().getId().equals(customer.getId())) {
            throw new ItemDoesNotBelongToUserException(itemId, customer.getId());
        }

        cartRepository.delete(cart);
    }

    public void modifyCart(Customer customer, Integer productId, Integer newQuantity) {
        Cart cartItem = cartRepository.findAllByCustomerIdAndProductId(customer.getId(), productId);
        cartItem.setQuantity(newQuantity);
        cartRepository.save(cartItem);
    }

}