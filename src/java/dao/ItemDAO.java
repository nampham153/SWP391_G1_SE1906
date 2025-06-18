package dao;

import context.DBContext;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import model.Item;
import model.ItemImage;

public class ItemDAO extends DBContext {

    public List<Item> getPCItems(int limit) {
        return getItemsByPrefix("P", limit);
    }

    public List<Item> getComponentItems(int limit) {
        return getItemsByPrefix("I", limit);
    }

    private List<Item> getItemsByPrefix(String prefix, int limit) {
        List<Item> list = new ArrayList<>();
        String query = "SELECT i.SerialNumber, i.ItemName, i.Price, i.Views, img.ImageContent " +
                       "FROM Item i LEFT JOIN ItemImage img ON i.SerialNumber = img.InventoryId " +
                       "WHERE i.SerialNumber LIKE ? " +
                       "ORDER BY i.Views DESC LIMIT ?";
        try (java.sql.Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, prefix + "%");
            ps.setInt(2, limit);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Item item = new Item();
                item.setSerialNumber(rs.getString("SerialNumber"));
                item.setItemName(rs.getString("ItemName"));
                item.setPrice(rs.getBigDecimal("Price"));
                item.setViews(rs.getInt("Views"));

                // Gán hình ảnh nếu có
                String imageContent = rs.getString("ImageContent");
                if (imageContent != null) {
                    ItemImage image = new ItemImage();
                    image.setImageContent(imageContent);
                    image.setInventoryId(rs.getString("SerialNumber"));
                    item.setImage(image);
                }

                list.add(item);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
