package grid.intern.storeApp.controller;

import grid.intern.storeApp.exceptions.customerExceptions.CustomerNotLoggedInException;
import grid.intern.storeApp.service.CheckoutService;
import grid.intern.storeApp.model.responses.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/checkout")
public class CheckoutController {
    private final CheckoutService checkoutService;

    public CheckoutController(CheckoutService checkoutService) {
        this.checkoutService = checkoutService;
    }

    @GetMapping("/")
    public ResponseEntity<ApiResponse> checkout(HttpServletRequest request) {
        if (request.getSession().getAttribute("user") == null) {    // check if customer is logged in
            throw new CustomerNotLoggedInException();
        }
        checkoutService.checkoutRequest(request);

        return new ResponseEntity<>(new ApiResponse(true, "Checkout completed."), HttpStatus.OK);
    }
}