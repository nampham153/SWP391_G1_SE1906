package dao;

import context.DBContext;
import model.Account;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class AccountDAO {

    // Phương thức kiểm tra đăng nhập
    public Account login(String email, String password) {
        String query = "SELECT a.Phone, a.Password, a.RoleId, c.CustomerId, c.CustomerName " +
                       "FROM Account a " +
                       "LEFT JOIN Customer c ON a.Phone = c.CustomerId " +
                       "WHERE a.Phone = ?";
        
        try (Connection conn = new DBContext().getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    String storedPassword = rs.getString("Password");
                    // Kiểm tra password (sử dụng BCrypt hoặc password encoder trong thực tế)
                    if (password.equals(storedPassword)) { // Trong thực tế nên dùng password encoder
                        Account account = new Account();
                        account.setPhone(rs.getString("Phone"));
                        account.setRoleId(rs.getInt("RoleId"));
                        account.setCustomerId(rs.getString("CustomerId"));
                        account.setCustomerName(rs.getString("CustomerName"));
                        return account;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // Phương thức đăng ký tài khoản mới
    public boolean register(String name, String email, String password, Date birthdate, boolean gender) {
        Connection conn = null;
        PreparedStatement psAccount = null;
        PreparedStatement psCustomer = null;
        
        try {
            conn = new DBContext().getConnection();
            conn.setAutoCommit(false); // Bắt đầu transaction
            
            // 1. Thêm vào bảng Account
            String sqlAccount = "INSERT INTO Account (Phone, Password, RoleId) VALUES (?, ?, ?)";
            psAccount = conn.prepareStatement(sqlAccount);
            psAccount.setString(1, email);
            psAccount.setString(2, password); // Trong thực tế nên mã hóa password
            psAccount.setInt(3, 2); // RoleId = 2 cho customer
            int accountResult = psAccount.executeUpdate();
            
            if (accountResult == 0) {
                conn.rollback();
                return false;
            }
            
            // 2. Thêm vào bảng Customer
            String sqlCustomer = "INSERT INTO Customer (CustomerId, CustomerName, CustomerEmail, CustomerBirthDate, CustomerGender) " +
                                "VALUES (?, ?, ?, ?, ?)";
            psCustomer = conn.prepareStatement(sqlCustomer);
            psCustomer.setString(1, email); // Sử dụng email làm CustomerId
            psCustomer.setString(2, name);
            psCustomer.setString(3, email);
            psCustomer.setDate(4, new java.sql.Date(birthdate.getTime()));
            psCustomer.setBoolean(5, gender);
            int customerResult = psCustomer.executeUpdate();
            
            if (customerResult == 0) {
                conn.rollback();
                return false;
            }
            
            conn.commit(); // Commit transaction nếu cả 2 thành công
            return true;
            
        } catch (Exception e) {
            if (conn != null) {
                try {
                    conn.rollback(); // Rollback nếu có lỗi
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (psAccount != null) psAccount.close();
                if (psCustomer != null) psCustomer.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // Kiểm tra email đã tồn tại chưa
    public boolean checkEmailExist(String email) {
        String query = "SELECT Phone FROM Account WHERE Phone = ?";
        
        try (Connection conn = new DBContext().getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next(); // Trả về true nếu email đã tồn tại
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}