package grid.intern.storeApp.model.dto;

import java.io.Serializable;

public class CustomerSessionDto implements Serializable {
    private Integer customerId;

    public CustomerSessionDto(Integer customerId) {
        this.customerId = customerId;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }
}
