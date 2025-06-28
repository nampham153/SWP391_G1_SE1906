/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author namp0
 */
import java.sql.Date;
import java.math.BigDecimal;

public class CustomerOrder {
    private int orderId;
    private Date orderDate;
    private String orderAddress;
    private String orderPhone;
    private String orderEmail;
    private BigDecimal shippingFee;
    private BigDecimal additionalFee;
    private BigDecimal total;
    private int orderStatus;
    private String note;
    private String customerId;
    // constructors, getters, setters...

    public CustomerOrder() {
    }

    public CustomerOrder(int orderId, Date orderDate, String orderAddress, String orderPhone, String orderEmail, BigDecimal shippingFee, BigDecimal additionalFee, BigDecimal total, int orderStatus, String note, String customerId) {
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

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public String getOrderAddress() {
        return orderAddress;
    }

    public void setOrderAddress(String orderAddress) {
        this.orderAddress = orderAddress;
    }

    public String getOrderPhone() {
        return orderPhone;
    }

    public void setOrderPhone(String orderPhone) {
        this.orderPhone = orderPhone;
    }

    public String getOrderEmail() {
        return orderEmail;
    }

    public void setOrderEmail(String orderEmail) {
        this.orderEmail = orderEmail;
    }

    public BigDecimal getShippingFee() {
        return shippingFee;
    }

    public void setShippingFee(BigDecimal shippingFee) {
        this.shippingFee = shippingFee;
    }

    public BigDecimal getAdditionalFee() {
        return additionalFee;
    }

    public void setAdditionalFee(BigDecimal additionalFee) {
        this.additionalFee = additionalFee;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public int getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(int orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }
    
    
    
}

