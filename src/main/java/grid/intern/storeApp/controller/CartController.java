package grid.intern.storeApp.controller;

import grid.intern.storeApp.model.dto.AddToCartDto;
import grid.intern.storeApp.model.dto.CustomerSessionDto;
import grid.intern.storeApp.model.entity.Customer;
import grid.intern.storeApp.service.CartService;
import grid.intern.storeApp.service.CustomerService;
import grid.intern.storeApp.util.ApiResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cart")
public class CartController {

    private final CartService cartService;
    private final CustomerService customerService;

    public CartController(CartService cartService, CustomerService customerService) {
        this.customerService = customerService;
        this.cartService = cartService;
    }


    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addToCart(@RequestBody AddToCartDto addToCartDto, HttpSession session) {
        CustomerSessionDto customerSessionDto = (CustomerSessionDto) session.getAttribute("user");
        Customer customer = customerService.findById(customerSessionDto.getSessionId());
        cartService.addToCart(addToCartDto, customer);

        return new ResponseEntity<>(new ApiResponse(true, "Added to Cart"), HttpStatus.CREATED);
    }

}
