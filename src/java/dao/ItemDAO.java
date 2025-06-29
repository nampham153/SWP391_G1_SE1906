package dao;

import context.DBContext;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import model.Item;
import model.ItemImage;

public class ItemDAO extends DBContext {

public List<Item> getPCItems(int limit) {
    List<Item> list = new ArrayList<>();
    String query = "SELECT i.SerialNumber, i.ItemName, i.Views, img.ImageContent "
                 + "FROM Item i LEFT JOIN ItemImage img ON i.SerialNumber = img.InventoryId "
                 + "WHERE i.SerialNumber LIKE 'PC%' "
                 + "ORDER BY i.Views DESC LIMIT ?";

    try (java.sql.Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(query)) {
        ps.setInt(1, limit);
        ResultSet rs = ps.executeQuery();

        ProductComponentDAO pcDAO = new ProductComponentDAO();

        while (rs.next()) {
            Item item = new Item();
            item.setSerialNumber(rs.getString("SerialNumber"));
            item.setItemName(rs.getString("ItemName"));
            item.setViews(rs.getInt("Views"));
            BigDecimal totalPrice = pcDAO.getTotalPriceOfProduct(item.getSerialNumber());
            item.setPrice(totalPrice); 
            String imageContent = rs.getString("ImageContent");
            if (imageContent != null) {
                ItemImage image = new ItemImage();
                image.setImageContent(imageContent);
                image.setInventoryId(item.getSerialNumber());
                item.setImage(image);
            }

            list.add(item);
        }
    } catch (Exception e) {
        e.printStackTrace();
    }

    return list;
}

    public List<Item> getComponentItems(int limit) {
        return getItemsByPrefix("I", limit);
    }

    private List<Item> getItemsByPrefix(String prefix, int limit) {
        List<Item> list = new ArrayList<>();
        String query = "SELECT i.SerialNumber, i.ItemName, i.Price, i.Views, img.ImageContent "
                + "FROM Item i LEFT JOIN ItemImage img ON i.SerialNumber = img.InventoryId "
                + "WHERE i.SerialNumber LIKE ? "
                + "ORDER BY i.Views DESC LIMIT ?";
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

    public Item getItemById(String serialNumber) {
        Item item = null;
        String query = "SELECT i.SerialNumber, i.ItemName, i.Price, i.Views, img.ImageContent "
                + "FROM Item i LEFT JOIN ItemImage img ON i.SerialNumber = img.InventoryId "
                + "WHERE i.SerialNumber = ?";
        try (java.sql.Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, serialNumber);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                item = new Item();
                item.setSerialNumber(rs.getString("SerialNumber"));
                item.setItemName(rs.getString("ItemName"));
                item.setPrice(rs.getBigDecimal("Price"));
                item.setViews(rs.getInt("Views"));

                String imageContent = rs.getString("ImageContent");
                if (imageContent != null) {
                    ItemImage image = new ItemImage();
                    image.setImageContent(imageContent);
                    image.setInventoryId(serialNumber);
                    item.setImage(image);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return item;
    }
public List<Item> getItemsByComponentCategory(int categoryId, int limit) {
    List<Item> list = new ArrayList<>();
    String query = """
        SELECT i.SerialNumber, i.ItemName, i.Stock, i.Price, i.Views, img.ImageContent
        FROM Item i
        JOIN Component c ON i.SerialNumber = c.ComponentId
        LEFT JOIN ItemImage img ON i.SerialNumber = img.InventoryId
        WHERE c.CategoryId = ?
        LIMIT ?
    """;
    try (java.sql.Connection conn = getConnection();
         PreparedStatement ps = conn.prepareStatement(query)) {
        ps.setInt(1, categoryId);
        ps.setInt(2, limit);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            Item item = new Item();
            item.setSerialNumber(rs.getString("SerialNumber"));
            item.setItemName(rs.getString("ItemName"));
            item.setStock(rs.getInt("Stock"));
            item.setPrice(rs.getBigDecimal("Price"));
            item.setViews(rs.getInt("Views"));

            ItemImage image = new ItemImage();
            image.setImageContent(rs.getString("ImageContent"));
            item.setImage(image);

            list.add(item);
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    return list;
}
public List<Item> searchItemsByName(String keyword) {
    List<Item> list = new ArrayList<>();
    String sql = "SELECT * FROM Item i LEFT JOIN ItemImage img ON i.SerialNumber = img.InventoryId WHERE i.ItemName LIKE ?";
    
    try (java.sql.Connection conn = getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {
        
        ps.setString(1, "%" + keyword + "%");
        ResultSet rs = ps.executeQuery();
        
        while (rs.next()) {
            Item item = new Item();
            item.setSerialNumber(rs.getString("SerialNumber"));
            item.setItemName(rs.getString("ItemName"));
            item.setPrice(rs.getBigDecimal("Price"));
            
            // Nếu có ảnh
            String imageContent = rs.getString("ImageContent");
            if (imageContent != null) {
                ItemImage img = new ItemImage();
                img.setImageContent(imageContent);
                item.setImage(img);
            }

            list.add(item);
        }
    } catch (Exception e) {
        e.printStackTrace();
    }

    return list;
}

public static void main(String[] args) {
    ItemDAO dao = new ItemDAO();
    List<Item> results = dao.searchItemsByName("Intel");

    System.out.println("Kết quả tìm thấy: " + results.size());
    for (Item item : results) {
        System.out.println("- " + item.getItemName() + " | " + item.getPrice() + " | " +
                           (item.getImage() != null ? item.getImage().getImageContent() : "No image"));
    }
}

}
