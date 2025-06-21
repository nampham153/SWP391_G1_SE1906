/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;
import context.DBContext;
import java.sql.Connection;
import java.sql.PreparedStatement;
import model.CustomerOrder;
/**
 *
 * @author namp0
 */
public class CustomerOrderDAO extends DBContext{
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
        ps.setBoolean(8, order.isOrderStatus());
        ps.setString(9, order.getNote());
        ps.setString(10, order.getCustomerId());
        ps.executeUpdate();
    } catch (Exception e) {
        e.printStackTrace();
    }
}

}
