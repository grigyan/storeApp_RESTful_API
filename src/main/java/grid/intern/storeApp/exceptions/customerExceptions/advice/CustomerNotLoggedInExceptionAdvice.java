package grid.intern.storeApp.exceptions.customerExceptions.advice;

import grid.intern.storeApp.exceptions.customerExceptions.CustomerNotLoggedInException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class CustomerNotLoggedInExceptionAdvice {
    @ResponseBody
    @ExceptionHandler(CustomerNotLoggedInException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    String customerNotLoggedInHandler(CustomerNotLoggedInException ex) {
        return ex.getMessage();
    }
}
