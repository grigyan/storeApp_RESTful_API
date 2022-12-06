package grid.intern.storeApp.util;

import grid.intern.storeApp.model.dto.CartDto;
import grid.intern.storeApp.model.entity.Product;

import java.util.List;

public class CartCost {
    private List<CartDto> cartItems;
    private double totalCost;

    public CartCost(List<CartDto> cartItems, double totalCost) {
        this.cartItems = cartItems;
        this.totalCost = totalCost;
    }

    public List<CartDto> getCartItems() {
        return cartItems;
    }

    public void setCartItems(List<CartDto> cartItems) {
        this.cartItems = cartItems;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }
}
