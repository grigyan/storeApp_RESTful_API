package grid.intern.storeApp.exceptions.cartExceptions;

public class ItemDoesNotBelongToUserException extends RuntimeException {
    public ItemDoesNotBelongToUserException(Integer itemId, Integer customerId) {
        super("Cart item: " + itemId + " does not belong to user: " + customerId);

    }
}
