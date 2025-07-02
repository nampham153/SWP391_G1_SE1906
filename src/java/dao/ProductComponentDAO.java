/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import context.DBContext;
import model.Item;
import model.ProductComponent;

import java.sql.*;
import java.util.*;
import java.math.BigDecimal;

public class ProductComponentDAO extends DBContext {

    // Lấy danh sách linh kiện của một sản phẩm
    public List<ProductComponent> getComponentsByProductId(String productId) {
        List<ProductComponent> list = new ArrayList<>();
        String sql = "SELECT * FROM ProductComponent WHERE ProductId = ?";
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, productId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ProductComponent pc = new ProductComponent(
                        rs.getString("ProductId"),
                        rs.getString("ComponentId"),
                        rs.getInt("Quantity")
                );
                list.add(pc);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // Lấy tổng giá trị sản phẩm = sum(Item.price × quantity)
    public BigDecimal getTotalPriceOfProduct(String productId) {
        BigDecimal total = BigDecimal.ZERO;
        String sql = """
            SELECT SUM(i.Price * pc.Quantity) AS Total
            FROM ProductComponent pc
            JOIN Item i ON pc.ComponentId = i.SerialNumber
            WHERE pc.ProductId = ?
        """;
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, productId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                total = rs.getBigDecimal("Total");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return total;
    }

    // Lấy chi tiết từng linh kiện (Item + quantity) của product
    public Map<Item, Integer> getComponentItemsWithQuantity(String productId) {
        Map<Item, Integer> result = new LinkedHashMap<>();
        String sql = """
            SELECT i.*, pc.Quantity
            FROM ProductComponent pc
            JOIN Item i ON pc.ComponentId = i.SerialNumber
            WHERE pc.ProductId = ?
        """;
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, productId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Item item = new Item(
                        rs.getString("SerialNumber"),
                        rs.getString("ItemName"),
                        rs.getInt("Stock"),
                        rs.getBigDecimal("Price"),
                        rs.getInt("Views"),
                        rs.getString("Description")
                );
                result.put(item, rs.getInt("Quantity"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}

