package grid.intern.storeApp.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Cart {
    @Id
    @GeneratedValue
    private Long id;
    private Integer customerId;
    private Integer productId;


    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
