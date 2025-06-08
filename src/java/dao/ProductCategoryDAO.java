package dao;

import context.DBContext;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.ProductCategory;

public class ProductCategoryDAO {
    private DBContext dbContext;

    public ProductCategoryDAO() {
        dbContext = new DBContext();
    }

    public void createProductCategory(String categoryName) {
        String sql = "INSERT INTO ProductCategory (CategoryName) VALUES (?)";
        try (Connection conn = dbContext.getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setString(1, categoryName);
            statement.executeUpdate();
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(ProductCategoryDAO.class.getName()).log(Level.SEVERE, "Lỗi khi tạo danh mục", ex);
        }
    }

    public ArrayList<ProductCategory> getAllProductCategory() {
        ArrayList<ProductCategory> list = new ArrayList<>();
        String sql = "SELECT CategoryId, CategoryName FROM ProductCategory";
        try (Connection conn = dbContext.getConnection();
             PreparedStatement statement = conn.prepareStatement(sql);
             ResultSet rs = statement.executeQuery()) {
            while (rs.next()) {
                ProductCategory pc = new ProductCategory(rs.getInt("CategoryId"), rs.getString("CategoryName"));
                list.add(pc);
            }
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(ProductCategoryDAO.class.getName()).log(Level.SEVERE, "Lỗi khi lấy danh sách danh mục", ex);
        }
        return list;
    }

    public void deleteProductCategory(int categoryId) {
        String sql = "DELETE FROM ProductCategory WHERE CategoryId = ?";
        try (Connection conn = dbContext.getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setInt(1, categoryId);
            statement.executeUpdate();
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(ProductCategoryDAO.class.getName()).log(Level.SEVERE, "Lỗi khi xóa danh mục", ex);
        }
    }

    public ArrayList<ProductCategory> searchProductCategory(String keyword) {
        ArrayList<ProductCategory> list = new ArrayList<>();
        String sql = "SELECT CategoryId, CategoryName FROM ProductCategory WHERE CategoryName LIKE ?";
        try (Connection conn = dbContext.getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setString(1, "%" + keyword + "%");
            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    ProductCategory pc = new ProductCategory(rs.getInt("CategoryId"), rs.getString("CategoryName"));
                    list.add(pc);
                }
            }
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(ProductCategoryDAO.class.getName()).log(Level.SEVERE, "Lỗi khi tìm kiếm danh mục", ex);
        }
        return list;
    }
}