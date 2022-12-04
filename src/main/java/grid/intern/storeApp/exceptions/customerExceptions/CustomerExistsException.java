package grid.intern.storeApp.exceptions.customerExceptions;

public class CustomerExistsException extends RuntimeException {
    public CustomerExistsException(String email) {
        super("Customer with email " + email + " already exists");
    }
}
