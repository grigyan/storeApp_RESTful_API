package grid.intern.storeApp.model.entity;

import grid.intern.storeApp.model.dto.CartDto;
import jakarta.persistence.*;

@Entity
@Table(name = "cart")
public class Cart {
    @Id
    @GeneratedValue
    private Integer id;

    @Column(name = "customer_id")
    private Integer customerId;

    @Column(name = "product_id")
    private Long productId;

    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Product product;

    private int quantity;

    public Cart() {}

    public Cart(CartDto dto, Product product, Integer customerId) {
        this.customerId = customerId;
        this.product = product;
        this.productId = dto.getProduct().getId();
        this.quantity = dto.getQuantity();
    }

    public Cart(Integer customerId, Long productId, int quantity) {
        this.quantity = quantity;
        this.customerId = customerId;
        this.productId = productId;
    }

    public Cart(CartDto dto, Product product) {
        this.product = product;
        this.productId = dto.getProduct().getId();
        this.quantity = dto.getQuantity();
    }


    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
