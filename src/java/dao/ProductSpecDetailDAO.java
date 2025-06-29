package dao;

import context.DBContext;
import model.Item;
import model.ItemImage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import model.ProductSpec;

public class ProductSpecDetailDAO extends DBContext {

   public Map<ProductSpec, List<Item>> getSpecDetailsByProduct(String productId) {
    Map<ProductSpec, List<Item>> result = new LinkedHashMap<>();
    String sql = """
        SELECT ps.SpecId, ps.SpecName, i.SerialNumber, i.ItemName, i.Price
        FROM ProductSpecDetail psd
        JOIN ProductSpec ps ON psd.SpecId = ps.SpecId
        JOIN Item i ON psd.SpecDetail = i.SerialNumber
        WHERE psd.ProductId = ?
        ORDER BY ps.SpecId;
    """;
    try (Connection conn = getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setString(1, productId);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            ProductSpec spec = new ProductSpec();
            spec.setSpecId(rs.getString("SpecId"));
            spec.setSpecName(rs.getString("SpecName"));

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

}
