package model;

public class Account {
    private String phone;
    private String password;
    private int roleId;
    private String customerId;
    private String customerName;
    private boolean status;

    // Constructors, getters and setters
    public Account() {
    }

    public Account(String phone, String password, int roleId, String customerId, String customerName, boolean status) {
        this.phone = phone;
        this.password = password;
        this.roleId = roleId;
        this.customerId = customerId;
        this.customerName = customerName;
        this.status = status;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

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

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Account{" + "phone=" + phone + ", password=" + password + ", roleId=" + roleId + ", customerId=" + customerId + ", customerName=" + customerName + ", status=" + status + '}';
    }
    
}