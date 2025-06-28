package dao;

import context.DBContext;
import model.CustomerOrder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class CustomerOrderDAO extends DBContext {

    public void insertCustomerOrder(CustomerOrder order) {
        String query = "INSERT INTO CustomerOrder (orderDate, orderAddress, orderPhone, orderEmail, shippingFee, additionalFee, total, orderStatus, note, customerId) "
                     + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setDate(1, order.getOrderDate());
            ps.setString(2, order.getOrderAddress());
            ps.setString(3, order.getOrderPhone());
            ps.setString(4, order.getOrderEmail());
            ps.setBigDecimal(5, order.getShippingFee());
            ps.setBigDecimal(6, order.getAdditionalFee());
            ps.setBigDecimal(7, order.getTotal());
            ps.setInt(8, order.getOrderStatus());
            ps.setString(9, order.getNote());
            ps.setString(10, order.getCustomerId());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int insertCustomerOrderReturnId(CustomerOrder order) {
        int orderId = -1;
        String query = "INSERT INTO CustomerOrder (orderDate, orderAddress, orderPhone, orderEmail, shippingFee, additionalFee, total, orderStatus, note, customerId) "
                     + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = getConnection(); 
             PreparedStatement ps = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            ps.setDate(1, order.getOrderDate());
            ps.setString(2, order.getOrderAddress());
            ps.setString(3, order.getOrderPhone());
            ps.setString(4, order.getOrderEmail());
            ps.setBigDecimal(5, order.getShippingFee());
            ps.setBigDecimal(6, order.getAdditionalFee());
            ps.setBigDecimal(7, order.getTotal());
            ps.setInt(8, order.getOrderStatus());
            ps.setString(9, order.getNote());
            ps.setString(10, order.getCustomerId());

            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                orderId = rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return orderId;
    }


    public void updateOrderStatus(int orderId, int newStatus) {
        String query = "UPDATE CustomerOrder SET orderStatus = ? WHERE orderId = ?";
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, newStatus);
            ps.setInt(2, orderId);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
