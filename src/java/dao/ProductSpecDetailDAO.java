package dao;

import context.DBContext;
import model.Item;
import model.ItemImage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import model.ProductSpec;

/**
 *
 * @author namp0
 */
public class ProductSpecDetailDAO extends DBContext {

    public Map<ProductSpec, List<Item>> getSpecDetailsByProduct(String productId) {
        Map<ProductSpec, List<Item>> result = new LinkedHashMap<>();
        // Cache để tránh tạo duplicate ProductSpec
        Map<String, ProductSpec> specCache = new HashMap<>();

        String sql = """
    SELECT ps.SpecId, ps.SpecName, i.SerialNumber, i.ItemName, i.Price
    FROM ProductSpecDetail psd
    JOIN ProductSpec ps ON psd.SpecId = ps.SpecId
    JOIN Item i ON psd.SpecDetail = i.SerialNumber
    WHERE psd.ProductId = ?
    ORDER BY ps.SpecId;
    """;

        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, productId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String specId = rs.getString("SpecId");
                String specName = rs.getString("SpecName");

                // Sử dụng cache để đảm bảo cùng một ProductSpec object cho cùng SpecId
                ProductSpec spec = specCache.computeIfAbsent(specId, k -> {
                    ProductSpec newSpec = new ProductSpec();
                    newSpec.setSpecId(specId);
                    newSpec.setSpecName(specName);
                    return newSpec;
                });

                Item item = new Item();
                item.setSerialNumber(rs.getString("SerialNumber"));
                item.setItemName(rs.getString("ItemName"));
                item.setPrice(rs.getBigDecimal("Price"));

                result.computeIfAbsent(spec, k -> new ArrayList<>()).add(item);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static void main(String[] args) {
        ProductSpecDetailDAO dao = new ProductSpecDetailDAO();
        String testProductId = "PC003";
        Map<ProductSpec, List<Item>> specMap = dao.getSpecDetailsByProduct(testProductId);
        System.out.println("===== Kết quả truy vấn các cấu hình biến thể =====");

        for (Map.Entry<ProductSpec, List<Item>> entry : specMap.entrySet()) {
            ProductSpec spec = entry.getKey();
            System.out.println("→ Spec: " + spec.getSpecId() + " | " + spec.getSpecName());

            for (Item item : entry.getValue()) {
                System.out.println("    - Item: " + item.getSerialNumber()
                        + " | " + item.getItemName()
                        + " | Giá: " + item.getPrice() + " VND");
            }
        }

        if (specMap.isEmpty()) {
            System.out.println("⚠️ Không tìm thấy biến thể nào cho productId = " + testProductId);
        }
    }

}
