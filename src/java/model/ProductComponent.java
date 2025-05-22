package model;

public class ProductComponent {

    private String productId;
    private String componentId;
    private int quantity;

    public ProductComponent() {
    }

    public ProductComponent(String productId, String componentId, int quantity) {
        this.productId = productId;
        this.componentId = componentId;
        this.quantity = quantity;
    }

    public String getProductId() {
        return productId;
    }

    public String getComponentId() {
        return componentId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public void setComponentId(String componentId) {
        this.componentId = componentId;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

}
