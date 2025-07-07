package dao;

import context.DBContext;
import model.CustomerOrder;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.CartItem;

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

            if (order.getCustomerId() == null) {
                ps.setNull(10, Types.VARCHAR);
            } else {
                ps.setString(10, order.getCustomerId());
            }

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

            if (order.getCustomerId() == null) {
                ps.setNull(10, Types.VARCHAR);
            } else {
                ps.setString(10, order.getCustomerId());
            }

            int rowsAffected = ps.executeUpdate();
            System.out.println("✅ Rows affected: " + rowsAffected);

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                orderId = rs.getInt(1);
                System.out.println("✅ Generated OrderId: " + orderId);
            } else {
                System.out.println("❌ No generated key returned.");
            }

        } catch (Exception e) {
            System.err.println("❌ Exception when inserting CustomerOrder:");
            e.printStackTrace();
        }
        return orderId;
    }

public void updateOrderStatus(CustomerOrder order) {
    String sql = "UPDATE CustomerOrder SET OrderStatus = ? WHERE OrderId = ?";
    try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setInt(1, order.getOrderStatus());
        ps.setInt(2, order.getOrderId());
        int rows = ps.executeUpdate();
        System.out.println("🛠️ UPDATE orderId = " + order.getOrderId() + ", status = " + order.getOrderStatus());
System.out.println("✅ Rows affected = " + rows);

    } catch (Exception e) {
        e.printStackTrace();
    }
}

    public CustomerOrder getOrderById(int orderId) {
    String sql = "SELECT * FROM CustomerOrder WHERE OrderId = ?";
    try (Connection conn = getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {

        ps.setInt(1, orderId);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            CustomerOrder order = new CustomerOrder();
            order.setOrderId(rs.getInt("OrderId"));
            order.setOrderDate(rs.getDate("OrderDate"));
            order.setOrderAddress(rs.getString("OrderAddress"));
            order.setOrderPhone(rs.getString("OrderPhone"));
            order.setOrderEmail(rs.getString("OrderEmail"));
            order.setShippingFee(rs.getBigDecimal("ShippingFee"));
            order.setAdditionalFee(rs.getBigDecimal("AdditionalFee"));
            order.setNote(rs.getString("Note"));
            order.setCustomerId(rs.getString("CustomerId"));
            order.setTotal(rs.getBigDecimal("Total"));
            order.setOrderStatus(rs.getInt("OrderStatus"));
            return order;
        }

    } catch (Exception e) {
        e.printStackTrace();
    }
    return null;
}
public List<CartItem> getCartItemsOfOrder(int orderId) {
    List<CartItem> list = new ArrayList<>();
    String sql = "SELECT ItemId, Quantity FROM OrderDetail WHERE OrderId = ?";

    try (Connection conn = getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {

        ps.setInt(1, orderId);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            CartItem item = new CartItem();
            item.setItemId(rs.getString("ItemId"));
            item.setQuantity(rs.getInt("Quantity"));
            list.add(item);
        }

    } catch (Exception e) {
        e.printStackTrace();
    }

    return list;
}




public static void main(String[] args) {
    CustomerOrderDAO dao = new CustomerOrderDAO();
    CustomerOrder testOrder = new CustomerOrder();
    testOrder.setOrderId(44);  
    testOrder.setOrderStatus(1); 

    dao.updateOrderStatus(testOrder); 
}

}
