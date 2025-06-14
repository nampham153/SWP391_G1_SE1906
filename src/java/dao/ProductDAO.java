package dao;

import context.DBContext;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Product;
import model.Item;

public class ProductDAO {
    private DBContext dbContext;

    public ProductDAO() {
        dbContext = new DBContext();
    }

    public void createProduct(String id, int categoryId, String itemName, int stock, double price, int views) {
        // Thêm Item trước
        String sqlItem = "INSERT INTO Item (SerialNumber, ItemName, Stock, Price, Views) VALUES (?, ?, ?, ?, ?)";
        // Thêm Product
        String sqlProduct = "INSERT INTO Product (ProductId, CategoryId) VALUES (?, ?)";
        try (Connection conn = dbContext.getConnection();
             PreparedStatement psItem = conn.prepareStatement(sqlItem);
             PreparedStatement psProduct = conn.prepareStatement(sqlProduct)) {
            // Thêm Item
            psItem.setString(1, id);
            psItem.setString(2, itemName);
            psItem.setInt(3, stock);
            psItem.setDouble(4, price);
            psItem.setInt(5, views);
            psItem.executeUpdate();
            // Thêm Product
            psProduct.setString(1, id);
            psProduct.setInt(2, categoryId);
            psProduct.executeUpdate();
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(ProductDAO.class.getName()).log(Level.SEVERE, "Lỗi khi tạo sản phẩm và item", ex);
        }
    }

    public ArrayList<Product> getAllProductsWithItems() {
        ArrayList<Product> list = new ArrayList<>();
        String sql = "SELECT p.ProductId, p.CategoryId, i.SerialNumber, i.ItemName, i.Stock, i.Price, i.Views " +
                     "FROM Product p LEFT JOIN Item i ON p.ProductId = i.SerialNumber";
        try (Connection conn = dbContext.getConnection();
             PreparedStatement statement = conn.prepareStatement(sql);
             ResultSet rs = statement.executeQuery()) {
            while (rs.next()) {
                Product p = new Product(rs.getString("ProductId"), rs.getInt("CategoryId"));
                // Gán thông tin Item nếu có
//                Item item = new Item(rs.getString("SerialNumber"), rs.getString("ItemName"), rs.getInt("Stock"),
//                        rs.getDouble("Price"), rs.getInt("Views"), 0); // categoryId tạm thời là 0
//                p.setItem(item); // Giả sử Product có phương thức setItem
                list.add(p);
            }
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(ProductDAO.class.getName()).log(Level.SEVERE, "Lỗi khi lấy danh sách sản phẩm", ex);
        }
        return list;
    }

    public Product getProductById(String productId) {
        String sql = "SELECT p.ProductId, p.CategoryId, i.SerialNumber, i.ItemName, i.Stock, i.Price, i.Views " +
                     "FROM Product p LEFT JOIN Item i ON p.ProductId = i.SerialNumber WHERE p.ProductId = ?";
        try (Connection conn = dbContext.getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setString(1, productId);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    Product p = new Product(rs.getString("ProductId"), rs.getInt("CategoryId"));
//                    Item item = new Item(rs.getString("SerialNumber"), rs.getString("ItemName"), rs.getInt("Stock"),
//                            rs.getDouble("Price"), rs.getInt("Views"), 0); // categoryId tạm thời là 0
//                    p.setItem(item); // Giả sử có setItem
//                    return p;
                }
            }
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(ProductDAO.class.getName()).log(Level.SEVERE, "Lỗi khi lấy sản phẩm", ex);
        }
        return null;
    }

    public void updateProduct(String id, int categoryId, String itemName, int stock, double price, int views) {
        String sqlProduct = "UPDATE Product SET CategoryId = ? WHERE ProductId = ?";
        String sqlItem = "UPDATE Item SET ItemName = ?, Stock = ?, Price = ?, Views = ? WHERE SerialNumber = ?";
        try (Connection conn = dbContext.getConnection();
             PreparedStatement psProduct = conn.prepareStatement(sqlProduct);
             PreparedStatement psItem = conn.prepareStatement(sqlItem)) {
            // Cập nhật Product
            psProduct.setInt(1, categoryId);
            psProduct.setString(2, id);
            psProduct.executeUpdate();
            // Cập nhật Item
            psItem.setString(1, itemName);
            psItem.setInt(2, stock);
            psItem.setDouble(3, price);
            psItem.setInt(4, views);
            psItem.setString(5, id);
            psItem.executeUpdate();
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(ProductDAO.class.getName()).log(Level.SEVERE, "Lỗi khi cập nhật sản phẩm", ex);
        }
    }

    public void deleteProduct(String productId) {
        String sqlProduct = "DELETE FROM Product WHERE ProductId = ?";
        String sqlItem = "DELETE FROM Item WHERE SerialNumber = ?";
        try (Connection conn = dbContext.getConnection();
             PreparedStatement psProduct = conn.prepareStatement(sqlProduct);
             PreparedStatement psItem = conn.prepareStatement(sqlItem)) {
            // Xóa Item trước
            psItem.setString(1, productId);
            psItem.executeUpdate();
            // Xóa Product
            psProduct.setString(1, productId);
            psProduct.executeUpdate();
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(ProductDAO.class.getName()).log(Level.SEVERE, "Lỗi khi xóa sản phẩm", ex);
        }
    }
}