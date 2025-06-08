/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

/**
 *
 * @author namp0
 */

import context.DBContext;
import model.Customer;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CustomerDAO extends DBContext {
    public Customer getCustomerByEmail(String email)
    {
        try {
            String query = "SELECT * FROM Customer WHERE CustomerEmail = ?";
            PreparedStatement stm = connection.prepareStatement(query);
            stm.setString(1, email);
            ResultSet rs = stm.executeQuery();
            if(rs.next())
            {
                Customer customer = new Customer();
                customer.setCustomerId(rs.getString("CustomerId"));
                customer.setCustomerName(rs.getString("CustomerName"));
                customer.setCustomerBirthDate(rs.getDate("CustomerBirthDate"));
                customer.setCustomerGender(rs.getBoolean("CustomerGender"));
                customer.setStatus(rs.getBoolean("Status"));
                customer.setCustomerEmail(email);
                return customer;
            }
        } catch (SQLException ex) {
            Logger.getLogger(CustomerDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public List<Customer> getAllCustomers() {
        List<Customer> list = new ArrayList<>();
        String query = "SELECT * FROM Customer";

        try (Connection conn = new DBContext().getConnection();
             PreparedStatement ps = conn.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(CustomerInfo(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public Customer getCustomerById(String id) {
        String query = "SELECT * FROM Customer WHERE CustomerId = ?";

        try (Connection conn = new DBContext().getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return CustomerInfo(rs);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public boolean insertCustomer(Customer c) {
        String query = "INSERT INTO Customer ("
                + "CustomerId, "
                + "CustomerName, "
                + "CustomerBirthDate, "
                + "CustomerGender, "
                + "CustomerEmail, "
                + "Status) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = new DBContext().getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, c.getCustomerId());
            ps.setString(2, c.getCustomerName());
            ps.setDate(3, new java.sql.Date(c.getCustomerBirthDate().getTime()));
            ps.setBoolean(4, c.isCustomerGender());
            ps.setString(5, c.getCustomerEmail());
            ps.setBoolean(6, c.getStatus());

            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean updateCustomer(Customer c) {
        String query = "UPDATE Customer SET "
                + "CustomerName = ?, "
                + "CustomerBirthDate = ?, "
                + "CustomerGender = ?, "
                + "CustomerEmail = ?, "
                + "Status = ? "
                + "WHERE CustomerId = ?";

        try (Connection conn = new DBContext().getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, c.getCustomerName());
            ps.setDate(2, new java.sql.Date(c.getCustomerBirthDate().getTime()));
            ps.setBoolean(3, c.isCustomerGender());
            ps.setString(4, c.getCustomerEmail());
            ps.setBoolean(5, c.getStatus());
            ps.setString(6, c.getCustomerId());

            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    // Xóa cũ - không xóa bản ghi thật, nên có thể bỏ hoặc giữ làm tham khảo
    public boolean deleteCustomer(String id) {
        String query = "DELETE FROM Customer WHERE CustomerId = ?";

        try (Connection conn = new DBContext().getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, id);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    // Hàm đổi trạng thái active sang inactive nếu khách hàng đang active
    public void changeStatusToInactiveIfActive(String id) {
        String sql = "UPDATE Customer SET Status = 0 WHERE CustomerId = ? AND Status = 1";
        try (Connection conn = new DBContext().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, id);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Tìm kiếm theo tên và trạng thái (có thể truyền null để bỏ qua điều kiện)
    public List<Customer> searchCustomersByNameAndStatus(String name, Boolean status) {
        List<Customer> list = new ArrayList<>();
        StringBuilder query = new StringBuilder("SELECT * FROM Customer WHERE 1=1");
        if (name != null && !name.trim().isEmpty()) {
            query.append(" AND CustomerName LIKE ?");
        }
        if (status != null) {
            query.append(" AND Status = ?");
        }

        try (Connection conn = new DBContext().getConnection();
             PreparedStatement ps = conn.prepareStatement(query.toString())) {

            int index = 1;
            if (name != null && !name.trim().isEmpty()) {
                ps.setString(index++, "%" + name + "%");
            }
            if (status != null) {
                ps.setBoolean(index++, status);
            }

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(CustomerInfo(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    private Customer CustomerInfo(ResultSet rs) throws SQLException {
        Customer c = new Customer();
        c.setCustomerId(rs.getString("CustomerId"));
        c.setCustomerName(rs.getString("CustomerName"));
        c.setCustomerBirthDate(rs.getDate("CustomerBirthDate"));
        c.setCustomerGender(rs.getBoolean("CustomerGender"));
        c.setCustomerEmail(rs.getString("CustomerEmail"));
        c.setStatus(rs.getBoolean("Status"));
        return c;
    }

    public boolean accountExists(String accountId) {
        String query = "SELECT COUNT(*) FROM Account WHERE Phone = ?";

        try (Connection conn = new DBContext().getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, accountId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                int count = rs.getInt(1);
                return count > 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public static void main(String[] args) {
        CustomerDAO dao = new CustomerDAO();
        List<Customer> list = dao.getAllCustomers();
        for (Customer c : list) {
            System.out.println(c.getCustomerId() + " - " + c.getCustomerName() + " - status: " + c.getStatus());
        }
    }
}
