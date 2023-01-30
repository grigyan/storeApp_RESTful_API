package grid.intern.storeApp.exceptions.customerExceptions;

public class CustomerEmailNotValidException extends RuntimeException {
    public CustomerEmailNotValidException(String email) {
        super("This email: " + email + " is not valid");
    }
}
