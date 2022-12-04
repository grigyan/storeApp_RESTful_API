package grid.intern.storeApp.exceptions.customerExceptions.advice;

import grid.intern.storeApp.exceptions.customerExceptions.CustomerNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@ControllerAdvice
public class CustomerNotFoundAdvice {
    @ResponseBody
    @ExceptionHandler(CustomerNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String customerExistsHandler(CustomerNotFoundException ex) {
        return ex.getMessage();
    }
}
