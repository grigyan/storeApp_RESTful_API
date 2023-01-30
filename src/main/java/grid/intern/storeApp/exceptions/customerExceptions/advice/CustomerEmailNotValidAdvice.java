package grid.intern.storeApp.exceptions.customerExceptions.advice;

import grid.intern.storeApp.exceptions.customerExceptions.CustomerEmailNotValidException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class CustomerEmailNotValidAdvice {

    @ResponseBody
    @ExceptionHandler(CustomerEmailNotValidException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    String customerExistsHandler(CustomerEmailNotValidException ex) {
        return ex.getMessage();
    }
}
