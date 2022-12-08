package grid.intern.storeApp.controller;

import grid.intern.storeApp.exceptions.cartExceptions.LowInStockException;
import grid.intern.storeApp.exceptions.customerExceptions.CustomerNotLoggedInException;
import grid.intern.storeApp.model.dto.AddToCartDto;
import grid.intern.storeApp.model.dto.CartDto;
import grid.intern.storeApp.model.dto.CartItemDto;
import grid.intern.storeApp.model.dto.CustomerSessionDto;
import grid.intern.storeApp.model.entity.Customer;
import grid.intern.storeApp.model.entity.Product;
import grid.intern.storeApp.service.CartService;
import grid.intern.storeApp.service.CustomerService;
import grid.intern.storeApp.service.ProductService;
import grid.intern.storeApp.util.ApiResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.http.HttpRequest;

@RestController
@RequestMapping("/cart")
public class CartController {

    private final CartService cartService;
    private final CustomerService customerService;
    private final ProductService productService;

    public CartController(CartService cartService, CustomerService customerService,
                          ProductService productService) {
        this.customerService = customerService;
        this.cartService = cartService;
        this.productService = productService;
    }


    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addToCart(@RequestBody AddToCartDto addToCartDto, HttpSession httpSession) {
        if (httpSession.getAttribute("user") == null) {
            throw new CustomerNotLoggedInException();
        }

        CustomerSessionDto customerSessionDto = (CustomerSessionDto) httpSession.getAttribute("user");
        Customer customer = customerService.findById(customerSessionDto.getSessionId());
        int available = productService.findById(addToCartDto.getProductId()).getAvailable();
        int toBeAdded = addToCartDto.getQuantity();
        if (toBeAdded > available) {
            throw new LowInStockException(available);
        }

        cartService.addToCart(addToCartDto, customer);
        return new ResponseEntity<>(new ApiResponse(true, "Added to Cart"), HttpStatus.CREATED);
    }

    @GetMapping("/")
    public ResponseEntity<CartDto> getCartItems(HttpSession httpSession) {
        if (httpSession.getAttribute("user") == null) {
            throw new CustomerNotLoggedInException();
        }

        CustomerSessionDto customerSessionDto = (CustomerSessionDto) httpSession.getAttribute("user");
        Customer customer = customerService.findById(customerSessionDto.getSessionId());
        CartDto cartDto = cartService.listAllItems(customer);
        return new ResponseEntity<>(cartDto, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{cartItemId}")
    public ResponseEntity<ApiResponse> deleteCartItem(@PathVariable Integer cartItemId, HttpSession httpSession) {
        if (httpSession.getAttribute("user") == null) {
            throw new CustomerNotLoggedInException();
        }

        CustomerSessionDto customerSessionDto = (CustomerSessionDto) httpSession.getAttribute("user");
        Customer customer = customerService.findById(customerSessionDto.getSessionId());
        cartService.deleteCartItem(cartItemId, customer);

        return new ResponseEntity<>(new ApiResponse(true, "Item has been removed"), HttpStatus.OK);
    }

}
