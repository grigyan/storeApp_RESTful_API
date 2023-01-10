package grid.intern.storeApp.controller;

import grid.intern.storeApp.exceptions.cartExceptions.LowInStockException;
import grid.intern.storeApp.exceptions.customerExceptions.CustomerNotLoggedInException;
import grid.intern.storeApp.model.dto.*;
import grid.intern.storeApp.model.entity.Customer;
import grid.intern.storeApp.service.CartService;
import grid.intern.storeApp.service.CustomerService;
import grid.intern.storeApp.service.ProductService;
import grid.intern.storeApp.util.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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


    @PostMapping("/")
    public ResponseEntity<ApiResponse> addToCart(@RequestBody AddToCartDto addToCartDto,  HttpServletRequest request) {
        if (request.getSession().getAttribute("user") == null) {
            throw new CustomerNotLoggedInException();
        }

        CustomerSessionDto customerSessionDto = (CustomerSessionDto) request.getSession().getAttribute("user");
        Customer customer = customerService.findById(customerSessionDto.getCustomerId());

        cartService.addToCart(addToCartDto, customer);
        return new ResponseEntity<>(new ApiResponse(true, "Added to Cart"), HttpStatus.CREATED);
    }

    @GetMapping("/")
    public ResponseEntity<CartDto> getCartItems(HttpServletRequest request) {
        if (request.getSession().getAttribute("user") == null) {
            throw new CustomerNotLoggedInException();
        }

        CustomerSessionDto customerSessionDto = (CustomerSessionDto) request.getSession().getAttribute("user");
        Customer customer = customerService.findById(customerSessionDto.getCustomerId());
        CartDto cartDto = cartService.listAllItems(customer);
        return new ResponseEntity<>(cartDto, HttpStatus.OK);
    }

    @DeleteMapping("/{cartItemId}")
    public ResponseEntity<ApiResponse> deleteCartItem(@PathVariable Integer cartItemId, HttpServletRequest request) {
        if (request.getSession().getAttribute("user") == null) {
            throw new CustomerNotLoggedInException();
        }

        CustomerSessionDto customerSessionDto = (CustomerSessionDto) request.getSession().getAttribute("user");
        Customer customer = customerService.findById(customerSessionDto.getCustomerId());
        cartService.deleteCartItem(cartItemId, customer);

        return new ResponseEntity<>(new ApiResponse(true, "Item has been removed"), HttpStatus.OK);
    }

    @PutMapping("/")
    public ResponseEntity<ApiResponse> modifyCartItem(@RequestBody ModifyCartItemDto modifyCartItemDto,
                                                      HttpServletRequest request) {
        // check if customer is logged in
        if (request.getSession().getAttribute("user") == null) {
            throw new CustomerNotLoggedInException();
        }

        // check for available quantity
        Integer productId = modifyCartItemDto.getProductId();
        Integer available = productService.findById(productId).getAvailable();
        Integer newQuantity = modifyCartItemDto.getProductQuantity();
        if (newQuantity > available) {
            throw new LowInStockException(available);
        }

        // get customer by customer_id
        CustomerSessionDto customerSessionDto = (CustomerSessionDto) request.getSession().getAttribute("user");
        Customer customer = customerService.findById(customerSessionDto.getCustomerId());
        cartService.modifyCart(customer, productId, newQuantity);


        return new ResponseEntity<>(new ApiResponse(true, "Item was modified"), HttpStatus.OK);
    }

}
