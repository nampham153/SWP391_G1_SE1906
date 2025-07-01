/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author namp0
 */


public class CartItem {
    private int cartId;
    private String itemId;
    private int quantity;
    private Item itemDetail;

    public CartItem() {}

    public CartItem(int cartId, String itemId, int quantity) {
        this.cartId = cartId;
        this.itemId = itemId;
        this.quantity = quantity;
    }

    public int getCartId() {
        return cartId;
    }

    public void setCartId(int cartId) {
        this.cartId = cartId;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Item getItemDetail() {
        return itemDetail;
    }

    public void setItemDetail(Item itemDetail) {
        this.itemDetail = itemDetail;
    }
}
