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
/**
 *
 * @author namp0
 */
public class ProductComponentDAO extends DBContext {

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
    // 1. Lấy giá gốc: tất cả các linh kiện mặc định trừ RAM và STORAGE
    // 2. Cộng thêm giá của các biến thể được chọn (RAM002|ST001)

    public BigDecimal getTotalPriceByVariant(String productId, String variantSignature) {
        BigDecimal total = BigDecimal.ZERO;

        String sql = """
        SELECT SUM(i.Price * pc.Quantity) AS Total
        FROM ProductComponent pc
        JOIN Item i ON pc.ComponentId = i.SerialNumber
        JOIN Component c ON i.SerialNumber = c.ComponentId
        JOIN ComponentCategory cc ON c.CategoryId = cc.CategoryId
        WHERE pc.ProductId = ? AND cc.CategoryName NOT IN ('RAM', 'STORAGE')
    """;

        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, productId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                total = rs.getBigDecimal("Total");
                if (total == null) {
                    total = BigDecimal.ZERO;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (variantSignature != null && !variantSignature.isEmpty()) {
            String[] componentIds = variantSignature.split("\\|");
            for (String componentId : componentIds) {
                Item item = new ItemDAO().getItemById(componentId);
                if (item != null && item.getPrice() != null) {
                    total = total.add(item.getPrice());
                }
            }
        }

        return total;
    }

    public static void main(String[] args) {
        ProductComponentDAO dao = new ProductComponentDAO();
        String itemId = "PC001";
        String variantSignature = "RAM:RAM002|Storage:ST002";

        BigDecimal price = dao.getTotalPriceByVariant(itemId, variantSignature);
        System.out.println("👉 Tổng giá của biến thể [" + variantSignature + "]: " + price + " VNĐ");
    }
}
