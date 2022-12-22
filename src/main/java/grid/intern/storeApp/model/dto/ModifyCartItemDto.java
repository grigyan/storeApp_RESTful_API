package grid.intern.storeApp.model.dto;

public class ModifyCartItemDto {
    private Integer productId;
    private Integer productQuantity;

    public ModifyCartItemDto() {}

    public ModifyCartItemDto(Integer productId, Integer productQuantity) {
        this.productId = productId;
        this.productQuantity = productQuantity;
    }

    public Integer getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(Integer productQuantity) {
        this.productQuantity = productQuantity;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }
}
