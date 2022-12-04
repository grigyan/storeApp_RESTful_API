package grid.intern.storeApp.exceptions.customerExceptions.advice;

import grid.intern.storeApp.exceptions.customerExceptions.CustomerExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class CustomerExistsAdvice {

    @ResponseBody
    @ExceptionHandler(CustomerExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    String customerExistsHandler(CustomerExistsException ex) {
        return ex.getMessage();
    }
}
