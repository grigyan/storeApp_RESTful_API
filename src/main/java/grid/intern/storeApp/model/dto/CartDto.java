package grid.intern.storeApp.model.dto;

import grid.intern.storeApp.model.entity.Cart;
import grid.intern.storeApp.model.entity.Product;

public class CartDto {  // For showing cart's products.
    private Integer id;
    private Integer customerId;

    private Integer quantity;
    private Product product;

    public CartDto() {}

    public CartDto(Cart cart) {
        this.setId(cart.getId());
        this.setCustomerId(cart.getCustomerId());
        this.setQuantity(cart.getQuantity());
        this.setProduct(cart.getProduct());
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    @Override
    public String toString() {
        return "CartDto{" +
                "id=" + id +
                ", userId=" + customerId +
                ", quantity=" + quantity +
                ", product=" + product +
                '}';
    }
}
