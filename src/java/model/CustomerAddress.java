/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author namp0
 */
public class CustomerAddress {
    private int customerAddressId;
    private String customerAddress;
    private String customerId;
    // constructors, getters, setters...

    public CustomerAddress() {
    }

    public CustomerAddress(int customerAddressId, String customerAddress, String customerId) {
        this.customerAddressId = customerAddressId;
        this.customerAddress = customerAddress;
        this.customerId = customerId;
    }

    public int getCustomerAddressId() {
        return customerAddressId;
    }

    public void setCustomerAddressId(int customerAddressId) {
        this.customerAddressId = customerAddressId;
    }

    public String getCustomerAddress() {
        return customerAddress;
    }

    public void setCustomerAddress(String customerAddress) {
        this.customerAddress = customerAddress;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    @Override
    public String toString() {
        return "CustomerAddress{" + "customerAddressId=" + customerAddressId + ", customerAddress=" + customerAddress + ", customerId=" + customerId + '}';
    }
    
}

