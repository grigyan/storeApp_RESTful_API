package grid.intern.storeApp.model.dto;

public class AddToCartDto {  // For adding/updating products into/inside the cart.
    private Integer id;
    private Integer productId;
    private Integer quantity;

    public AddToCartDto() {}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
