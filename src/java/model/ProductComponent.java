/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author namp0
 */
public class ProductComponent {
    private String productId;
    private String componentId;
    private int quantity;
    // constructors, getters, setters...

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

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getComponentId() {
        return componentId;
    }

    public void setComponentId(String componentId) {
        this.componentId = componentId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "ProductComponent{" + "productId=" + productId + ", componentId=" + componentId + ", quantity=" + quantity + '}';
    }
    
}

