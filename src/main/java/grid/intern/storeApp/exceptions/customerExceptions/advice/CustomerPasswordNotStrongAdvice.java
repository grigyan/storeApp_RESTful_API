package grid.intern.storeApp.exceptions.customerExceptions.advice;

import grid.intern.storeApp.exceptions.customerExceptions.CustomerPasswordNotStrongException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class CustomerPasswordNotStrongAdvice {
    @ResponseBody
    @ExceptionHandler(CustomerPasswordNotStrongException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    String customerExistsHandler(CustomerPasswordNotStrongException ex) {
        return ex.getMessage();
    }
}
