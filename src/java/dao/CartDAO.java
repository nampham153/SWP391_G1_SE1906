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
import model.Cart;

import java.sql.*;
import model.CartItem;

public class CartDAO extends DBContext {

    public Cart getCartByCustomerId(String customerId) {
        String sql = "SELECT * FROM Cart WHERE CustomerId = ?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, customerId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Cart(
                        rs.getInt("CartId"),
                        rs.getString("CustomerId"),
                        rs.getTimestamp("CreatedDate")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public int createCart(String customerId) {
        String sql = "INSERT INTO Cart (CustomerId) VALUES (?)";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, customerId);
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1); // CartId
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

}

