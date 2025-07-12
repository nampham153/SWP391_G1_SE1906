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
public BigDecimal getTotalPriceByVariant(String itemId, String variantSignature) {
    BigDecimal total = BigDecimal.ZERO;

    // Giá gốc của PC
    Item baseItem = new ItemDAO().getItemById(itemId);
    if (baseItem != null && baseItem.getPrice() != null) {
        total = total.add(baseItem.getPrice());
    }

    // Giá các linh kiện tùy chọn
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

    // Test 1: Biến thể có đầy đủ thông tin hợp lệ
    String itemId = "PC001";  // mã sản phẩm chính (không dùng trong tính giá cụ thể ở đây)
    String variantSignature = "RAM:RAM002|Storage:ST002";

    BigDecimal price = dao.getTotalPriceByVariant(itemId, variantSignature);
    System.out.println("👉 Tổng giá của biến thể [" + variantSignature + "]: " + price + " VNĐ");

//    // Test 2: Không có variant (trả về giá mặc định của PC)
//    String defaultVariant = "";
//    BigDecimal defaultPrice = dao.getTotalPriceByVariant(itemId, defaultVariant);
//    System.out.println("👉 Giá mặc định của PC [" + itemId + "]: " + defaultPrice + " VNĐ");
//
//    // Test 3: Variant không hợp lệ
//    String invalidVariant = "RAM|SSD";  // format sai
//    BigDecimal priceInvalid = dao.getTotalPriceByVariant(itemId, invalidVariant);
//    System.out.println("👉 Giá với biến thể sai cú pháp: " + priceInvalid + " VNĐ");
//
//    // Test 4: Component không tồn tại
//    String variantNotExist = "CPU:XYZ999|GPU:ABC000";
//    BigDecimal priceNotExist = dao.getTotalPriceByVariant(itemId, variantNotExist);
//    System.out.println("👉 Giá với linh kiện không tồn tại: " + priceNotExist + " VNĐ");
}


}

