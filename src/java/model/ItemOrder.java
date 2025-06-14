/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.math.BigDecimal;


/**
 *
 * @author namp0
 */
public class ItemOrder {
    private String itemId;
    private int orderId;
    private int quantity;
    private BigDecimal listPrice;
    // constructors, getters, setters...

    public ItemOrder() {
    }

    public ItemOrder(String itemId, int orderId, int quantity, BigDecimal listPrice) {
        this.itemId = itemId;
        this.orderId = orderId;
        this.quantity = quantity;
        this.listPrice = listPrice;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getListPrice() {
        return listPrice;
    }

    public void setListPrice(BigDecimal listPrice) {
        this.listPrice = listPrice;
    }

    @Override
    public String toString() {
        return "ItemOrder{" + "itemId=" + itemId + ", orderId=" + orderId + ", quantity=" + quantity + ", listPrice=" + listPrice + '}';
    }
    
}

