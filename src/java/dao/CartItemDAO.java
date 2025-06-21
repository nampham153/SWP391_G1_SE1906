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
import model.CartItem;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CartItemDAO extends DBContext {

    public void addOrUpdateItem(int cartId, String itemId, int quantity) {
        String sql = "INSERT INTO CartItem (CartId, ItemId, Quantity) VALUES (?, ?, ?) " +
                     "ON DUPLICATE KEY UPDATE Quantity = Quantity + ?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, cartId);
            ps.setString(2, itemId);
            ps.setInt(3, quantity);
            ps.setInt(4, quantity);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateQuantity(int cartId, String itemId, int newQuantity) {
        String sql = "UPDATE CartItem SET Quantity = ? WHERE CartId = ? AND ItemId = ?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, newQuantity);
            ps.setInt(2, cartId);
            ps.setString(3, itemId);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void removeItem(int cartId, String itemId) {
        String sql = "DELETE FROM CartItem WHERE CartId = ? AND ItemId = ?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, cartId);
            ps.setString(2, itemId);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<CartItem> getItemsInCart(int cartId) {
        List<CartItem> list = new ArrayList<>();
        String sql = "SELECT * FROM CartItem WHERE CartId = ?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, cartId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new CartItem(
                        rs.getInt("CartId"),
                        rs.getString("ItemId"),
                        rs.getInt("Quantity")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public CartItem getCartItem(int cartId, String itemId) {
        String sql = "SELECT * FROM CartItem WHERE CartId = ? AND ItemId = ?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, cartId);
            ps.setString(2, itemId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new CartItem(
                        rs.getInt("CartId"),
                        rs.getString("ItemId"),
                        rs.getInt("Quantity")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
