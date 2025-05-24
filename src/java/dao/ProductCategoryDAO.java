/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import context.DbContext;
import model.ProductCategory;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author DELL
 */
public class ProductCategoryDAO extends DbContext {

    public void createProductCategory(String categoryName) {
        try {
            String sql = "INSERT INTO [ProductCategory] (CategoryName) VALUES (?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, categoryName);
            statement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(ProductCategoryDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public List<ProductCategory> getAllProductCategories() {
        List<ProductCategory> list = new ArrayList<>();
        try {
            String sql = "SELECT CategoryId, CategoryName FROM [ProductCategory]";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                ProductCategory pc = new ProductCategory(
                    rs.getInt("CategoryId"),
                    rs.getString("CategoryName")
                );
                list.add(pc);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProductCategoryDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public ProductCategory getProductCategoryById(int id) {
        try {
            String sql = "SELECT CategoryId, CategoryName FROM [ProductCategory] WHERE CategoryId = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                return new ProductCategory(
                    rs.getInt("CategoryId"),
                    rs.getString("CategoryName")
                );
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProductCategoryDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public void updateProductCategory(int id, String categoryName) {
        try {
            String sql = "UPDATE [ProductCategory] SET CategoryName = ? WHERE CategoryId = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, categoryName);
            statement.setInt(2, id);
            statement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(ProductCategoryDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void deleteProductCategory(int id) {
        try {
            String sql = "DELETE FROM [ProductCategory] WHERE CategoryId = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(ProductCategoryDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}