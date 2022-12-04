package grid.intern.storeApp.exceptions.customerExceptions;

public class CustomerNotFoundException extends RuntimeException {
    public CustomerNotFoundException(Long id) {
        super("Could not find customer with id: " + id);
    }

    public CustomerNotFoundException(String email) {
        super("No user found with this email: " + email);
    }
}
