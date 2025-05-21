package model;

import java.util.Date;

public class Customer {

    private String customerId;
    private String customerName;
    private String customerEmail;
    private Date customerBirthDate;
    private boolean customerGender;

    public Customer() {
    }

    public Customer(String customerId, String customerName, String customerEmail,
            Date customerBirthDate, boolean customerGender) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.customerEmail = customerEmail;
        this.customerBirthDate = customerBirthDate;
        this.customerGender = customerGender;
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

    @Override
    public String toString() {
        return "Customer{"
                + "customerId='" + customerId + '\''
                + ", customerName='" + customerName + '\''
                + ", customerEmail='" + customerEmail + '\''
                + ", customerBirthDate=" + customerBirthDate
                + ", customerGender=" + customerGender
                + '}';
    }
}
