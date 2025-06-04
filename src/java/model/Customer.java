package model;

import java.sql.Date;

public class Customer {

    private String customerId;
    private String customerName;
    private String customerEmail;
    private Date customerBirthDate;
    private boolean customerGender;
    private boolean status;

    public Customer() {
    }

    public Customer(String customerId, String customerName, String customerEmail, Date customerBirthDate, boolean customerGender, boolean status) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.customerEmail = customerEmail;
        this.customerBirthDate = customerBirthDate;
        this.customerGender = customerGender;
        this.status = status;
    }

    @Override
    public String toString() {
        return "Customer{" + "customerId=" + customerId + ", customerName=" + customerName + ", customerEmail=" + customerEmail + ", customerBirthDate=" + customerBirthDate + ", customerGender=" + customerGender + ", status=" + status + '}';
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    // Getters and Setters
    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public Date getCustomerBirthDate() {
        return customerBirthDate;
    }

    public void setCustomerBirthDate(Date customerBirthDate) {
        this.customerBirthDate = customerBirthDate;
    }

    public boolean isCustomerGender() {
        return customerGender;
    }

    public void setCustomerGender(boolean customerGender) {
        this.customerGender = customerGender;
    }

    public boolean isStatus() {
        return status;
    }
    
    
}
