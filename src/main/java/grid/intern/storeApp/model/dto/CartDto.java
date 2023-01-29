package grid.intern.storeApp.model.dto;

import java.util.List;

public class CartDto {  // For showing cart's products.
    private List<CartItemDto> cartItemList;
    private double totalCost;

    public CartDto() {}

    public List<CartItemDto> getCartItemList() {
        return cartItemList;
    }

    public void setCartItemList(List<CartItemDto> cartItemList) {
        this.cartItemList = cartItemList;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }
}
