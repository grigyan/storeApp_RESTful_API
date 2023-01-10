package grid.intern.storeApp.controller;

import grid.intern.storeApp.exceptions.cartExceptions.LowInStockException;
import grid.intern.storeApp.exceptions.customerExceptions.CustomerNotLoggedInException;
import grid.intern.storeApp.model.dto.CustomerSessionDto;
import grid.intern.storeApp.model.entity.Cart;
import grid.intern.storeApp.model.entity.Product;
import grid.intern.storeApp.service.CartService;
import grid.intern.storeApp.service.CustomerService;
import grid.intern.storeApp.model.responses.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/checkout")
public class CheckoutController {
    private final CustomerService customerService;
    private final CartService cartService;

    public CheckoutController(CustomerService customerService, CartService cartService) {
        this.customerService = customerService;
        this.cartService = cartService;
    }

    @GetMapping("/")
    public ResponseEntity<ApiResponse> checkout(HttpServletRequest request) {
        if (request.getSession().getAttribute("user") == null) {    // check if customer is logged in
            throw new CustomerNotLoggedInException();
        }

        CustomerSessionDto customerSessionDto = (CustomerSessionDto) request.getSession().getAttribute("user");
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

        return new ResponseEntity<>(new ApiResponse(true, "Checkout completed."), HttpStatus.OK);
    }





}
