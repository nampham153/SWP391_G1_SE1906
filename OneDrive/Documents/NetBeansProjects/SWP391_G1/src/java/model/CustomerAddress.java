package model;

public class CustomerAddress {

    private int customerAddressId;
    private String customerAddressContent;
    private String customerId;

    public CustomerAddress() {
    }

    public CustomerAddress(int customerAddressId, String customerAddressContent, String customerId) {
        this.customerAddressId = customerAddressId;
        this.customerAddressContent = customerAddressContent;
        this.customerId = customerId;
    }

    public int getCustomerAddressId() {
        return customerAddressId;
    }

    public String getCustomerAddressContent() {
        return customerAddressContent;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerAddressId(int customerAddressId) {
        this.customerAddressId = customerAddressId;
    }

    public void setCustomerAddressContent(String customerAddressContent) {
        this.customerAddressContent = customerAddressContent;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

}
