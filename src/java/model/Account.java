package model;

public class Account {
    private String phone;
    private String password;
    private int roleId;

    // Constructors, getters and setters
    public Account() {
    }

    public Account(String phone, String password, int roleId) {
        this.phone = phone;
        this.password = password;
        this.roleId = roleId;
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

    @Override
    public String toString() {
        return "Account{" + "phone=" + phone + ", password=" + password + ", roleId=" + roleId + '}';
    }
    
}