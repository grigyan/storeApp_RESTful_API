package grid.intern.storeApp.service;

import grid.intern.storeApp.exceptions.cartExceptions.ItemDoesNotBelongToUserException;
import grid.intern.storeApp.exceptions.cartExceptions.ItemNotFoundException;
import grid.intern.storeApp.exceptions.cartExceptions.LowInStockException;
import grid.intern.storeApp.model.dto.*;
import grid.intern.storeApp.model.entity.Cart;
import grid.intern.storeApp.model.entity.Customer;
import grid.intern.storeApp.model.entity.Product;
import grid.intern.storeApp.repository.CartRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class CartService {
    private final CartRepository cartRepository;
    private final ProductService productService;
    private final CustomerService customerService;

    public CartService(CartRepository cartRepository, ProductService productService, CustomerService customerService) {
        this.productService = productService;
        this.cartRepository = cartRepository;
        this.customerService = customerService;
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

    public CartDto getCartInfo(Integer customerId) {
        Customer customer = customerService.findById(customerId);
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

    @Transactional
    public void deleteCartItem(Integer itemId, Customer customer) {
        Cart cart = cartRepository.findById(itemId)
                .orElseThrow(() -> new ItemNotFoundException(itemId));

        if (!cart.getCustomer().getId().equals(customer.getId())) {
            throw new ItemDoesNotBelongToUserException(itemId, customer.getId());
        }

        cartRepository.delete(cart);
    }

    @Transactional
    public void modifyCartItem(ModifyCartItemDto modifyCartItemDto, CustomerSessionDto customerSessionDto) {
        Integer productId = modifyCartItemDto.getProductId();
        Integer newQuantity = modifyCartItemDto.getProductQuantity();
        Integer available = productService.findById(productId).getAvailable();

        if (newQuantity > available) {
            throw new LowInStockException(available);
        }

        Customer customer = customerService.findById(customerSessionDto.getCustomerId());
        Cart cartItem = cartRepository.findAllByCustomerIdAndProductId(customer.getId(), productId);
        cartItem.setQuantity(newQuantity);
        cartRepository.save(cartItem);
    }

}