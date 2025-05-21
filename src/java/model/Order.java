package model;

import java.util.Date;

public class Order {

    private int orderId;
    private Date orderDate;
    private String orderAddress;
    private String orderPhone;
    private String orderEmail;
    private double shippingFee;
    private double additionalFee;
    private double total;
    private boolean orderStatus;
    private String note;
    private String customerId;

    public Order() {
    }

    public Order(int orderId, Date orderDate, String orderAddress, String orderPhone,
            String orderEmail, double shippingFee, double additionalFee,
            double total, boolean orderStatus, String note, String customerId) {
        this.orderId = orderId;
        this.orderDate = orderDate;
        this.orderAddress = orderAddress;
        this.orderPhone = orderPhone;
        this.orderEmail = orderEmail;
        this.shippingFee = shippingFee;
        this.additionalFee = additionalFee;
        this.total = total;
        this.orderStatus = orderStatus;
        this.note = note;
        this.customerId = customerId;
    }

    public int getOrderId() {
        return orderId;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public String getOrderAddress() {
        return orderAddress;
    }

    public String getOrderPhone() {
        return orderPhone;
    }

    public String getOrderEmail() {
        return orderEmail;
    }

    public double getShippingFee() {
        return shippingFee;
    }

    public double getAdditionalFee() {
        return additionalFee;
    }

    public double getTotal() {
        return total;
    }

    public boolean isOrderStatus() {
        return orderStatus;
    }

    public String getNote() {
        return note;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public void setOrderAddress(String orderAddress) {
        this.orderAddress = orderAddress;
    }

    public void setOrderPhone(String orderPhone) {
        this.orderPhone = orderPhone;
    }

    public void setOrderEmail(String orderEmail) {
        this.orderEmail = orderEmail;
    }

    public void setShippingFee(double shippingFee) {
        this.shippingFee = shippingFee;
    }

    public void setAdditionalFee(double additionalFee) {
        this.additionalFee = additionalFee;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public void setOrderStatus(boolean orderStatus) {
        this.orderStatus = orderStatus;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

}
