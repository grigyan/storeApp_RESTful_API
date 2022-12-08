package grid.intern.storeApp.exceptions.cartExceptions.advice;

import grid.intern.storeApp.exceptions.cartExceptions.LowInStockException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class LowInStockExceptionAdvice {
    @ResponseBody
    @ExceptionHandler(LowInStockException.class)
    @ResponseStatus(HttpStatus.OK)
    String lowInStock(LowInStockException ex) {
        return ex.getMessage();
    }
}
