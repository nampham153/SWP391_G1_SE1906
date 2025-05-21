package model;

public class ItemOrder {

    private String itemId;
    private int orderId;
    private int quantity;
    private double listPrice;

    public ItemOrder() {
    }

    public ItemOrder(String itemId, int orderId, int quantity, double listPrice) {
        this.itemId = itemId;
        this.orderId = orderId;
        this.quantity = quantity;
        this.listPrice = listPrice;
    }

    public String getItemId() {
        return itemId;
    }

    public int getOrderId() {
        return orderId;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getListPrice() {
        return listPrice;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setListPrice(double listPrice) {
        this.listPrice = listPrice;
    }

}
