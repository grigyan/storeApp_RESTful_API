package grid.intern.storeApp.exceptions.cartExceptions;

public class LowInStockException extends RuntimeException {
    public LowInStockException(int available) {
        super("Low in stock. Only available " + available);
    }

}
