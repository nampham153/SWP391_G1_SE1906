package dao;

import context.DBContext;
import model.CartItem;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author namp0
 */
public class CartItemDAO extends DBContext {

    // Thêm mới hoặc cập nhật số lượng sản phẩm (theo variantSignature)
    public void addOrUpdateItem(int cartId, String itemId, String variantSignature, int quantity) {
        String sql = "INSERT INTO CartItem (CartId, ItemId, VariantSignature, Quantity) VALUES (?, ?, ?, ?) " +
                     "ON DUPLICATE KEY UPDATE Quantity = Quantity + ?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, cartId);
            ps.setString(2, itemId);
            ps.setString(3, variantSignature);
            ps.setInt(4, quantity);
            ps.setInt(5, quantity);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Cập nhật số lượng mới cho một sản phẩm (theo variantSignature)
    public void updateQuantity(int cartId, String itemId, String variantSignature, int newQuantity) {
        String sql = "UPDATE CartItem SET Quantity = ? WHERE CartId = ? AND ItemId = ? AND VariantSignature = ?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, newQuantity);
            ps.setInt(2, cartId);
            ps.setString(3, itemId);
            ps.setString(4, variantSignature);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void removeItem(int cartId, String itemId, String variantSignature) {
        String sql = "DELETE FROM CartItem WHERE CartId = ? AND ItemId = ? AND VariantSignature = ?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, cartId);
            ps.setString(2, itemId);
            ps.setString(3, variantSignature);
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
                CartItem item = new CartItem(
                        rs.getInt("CartId"),
                        rs.getString("ItemId"),
                        rs.getString("VariantSignature"),
                        rs.getInt("Quantity")
                );
                list.add(item);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // Lấy một sản phẩm cụ thể (theo variantSignature)
    public CartItem getCartItem(int cartId, String itemId, String variantSignature) {
        String sql = "SELECT * FROM CartItem WHERE CartId = ? AND ItemId = ? AND VariantSignature = ?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, cartId);
            ps.setString(2, itemId);
            ps.setString(3, variantSignature);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new CartItem(
                        rs.getInt("CartId"),
                        rs.getString("ItemId"),
                        rs.getString("VariantSignature"),
                        rs.getInt("Quantity")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public int countItemsByCustomerId(String customerId) {
        String sql = """
            SELECT SUM(Quantity) AS TotalQuantity
            FROM CartItem ci
            JOIN Cart c ON ci.CartId = c.CartId
            WHERE c.CustomerId = ?
        """;
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, customerId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("TotalQuantity");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public void clearCart(int cartId) {
        String sql = "DELETE FROM CartItem WHERE CartId = ?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, cartId);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
