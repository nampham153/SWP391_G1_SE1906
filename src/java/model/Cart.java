package model;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author namp0
 */


import java.sql.Timestamp;

public class Cart {
    private int cartId;
    private String customerId;
    private Timestamp createdDate;

    public Cart() {}

    public Cart(int cartId, String customerId, Timestamp createdDate) {
        this.cartId = cartId;
        this.customerId = customerId;
        this.createdDate = createdDate;
    }

    public int getCartId() {
        return cartId;
    }

    public void setCartId(int cartId) {
        this.cartId = cartId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public Timestamp getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Timestamp createdDate) {
        this.createdDate = createdDate;
    }
}

