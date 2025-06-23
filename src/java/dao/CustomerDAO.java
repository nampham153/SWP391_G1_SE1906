package dao;

import context.DBContext;
import model.Customer;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CustomerDAO extends DBContext {

    public Customer getCustomerByEmail(String email) throws ClassNotFoundException {
        String query = "SELECT * FROM Customer WHERE CustomerEmail = ?";
        try (Connection conn = getConnection(); PreparedStatement stm = conn.prepareStatement(query)) {
            stm.setString(1, email);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                return getCustomerFromResultSet(rs);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CustomerDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public List<Customer> getAllCustomers() {
        List<Customer> list = new ArrayList<>();
        String query = "SELECT * FROM Customer";
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(query); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(getCustomerFromResultSet(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public Customer getCustomerById(String id) {
        String query = "SELECT * FROM Customer WHERE CustomerId = ?";
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return getCustomerFromResultSet(rs);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean insertCustomer(Customer c) {
        String query = "INSERT INTO Customer (CustomerId, CustomerName, CustomerBirthDate, CustomerGender, CustomerEmail, Status) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(query)) {
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
        String query = "UPDATE Customer SET CustomerName = ?, CustomerBirthDate = ?, CustomerGender = ?, CustomerEmail = ?, Status = ? WHERE CustomerId = ?";
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(query)) {
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

    public boolean deleteCustomer(String id) {
        String query = "DELETE FROM Customer WHERE CustomerId = ?";
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, id);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    private Customer getCustomerFromResultSet(ResultSet rs) throws SQLException {
    Customer c = new Customer();
    c.setCustomerId(rs.getString("CustomerId"));
    c.setCustomerName(rs.getString("CustomerName"));
    c.setCustomerEmail(rs.getString("CustomerEmail"));
    c.setCustomerBirthDate(rs.getDate("CustomerBirthDate"));
    c.setCustomerGender(rs.getBoolean("CustomerGender"));
    c.setStatus(rs.getObject("Status") != null ? rs.getBoolean("Status") : null);
    return c;
}
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
