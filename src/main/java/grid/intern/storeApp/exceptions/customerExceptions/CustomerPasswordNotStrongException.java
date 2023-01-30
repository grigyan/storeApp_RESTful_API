package grid.intern.storeApp.exceptions.customerExceptions;


public class CustomerPasswordNotStrongException extends RuntimeException {
    public CustomerPasswordNotStrongException(String password) {
        super("Password is not strong enough");
    }
}
