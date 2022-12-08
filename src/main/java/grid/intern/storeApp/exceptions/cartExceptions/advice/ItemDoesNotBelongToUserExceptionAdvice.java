package grid.intern.storeApp.exceptions.cartExceptions.advice;

import grid.intern.storeApp.exceptions.cartExceptions.ItemDoesNotBelongToUserException;
import grid.intern.storeApp.exceptions.cartExceptions.ItemNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ItemDoesNotBelongToUserExceptionAdvice {
    @ResponseBody
    @ExceptionHandler(ItemDoesNotBelongToUserException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String itemDoesNotBelongToUser(ItemDoesNotBelongToUserException ex) {
        return ex.getMessage();
    }
}
