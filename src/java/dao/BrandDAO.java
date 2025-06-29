/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;
import context.DBContext;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import model.Brand;
/**
 *
 * @author namp0
 */
public class BrandDAO extends DBContext {
    public List<Brand> getAllProductBrands() {
        List<Brand> list = new ArrayList<>();
        String sql = "SELECT DISTINCT b.BrandId, b.BrandName FROM Brand b JOIN BrandProductCategory bp ON b.BrandId = bp.BrandId";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Brand b = new Brand();
                b.setBrandId(rs.getInt("BrandId"));
                b.setBrandName(rs.getString("BrandName"));
                list.add(b);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Brand> getAllComponentBrands() {
        List<Brand> list = new ArrayList<>();
        String sql = "SELECT DISTINCT b.BrandId, b.BrandName FROM Brand b JOIN BrandComponentCategory bc ON b.BrandId = bc.BrandId";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Brand b = new Brand();
                b.setBrandId(rs.getInt("BrandId"));
                b.setBrandName(rs.getString("BrandName"));
                list.add(b);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    // Lọc brand theo Product Category
public List<Brand> getProductBrandsByCategory(int categoryId) {
    List<Brand> list = new ArrayList<>();
    String sql = "SELECT b.BrandId, b.BrandName FROM Brand b " +
                 "JOIN BrandProductCategory bp ON b.BrandId = bp.BrandId " +
                 "WHERE bp.CategoryId = ?";
    try (Connection conn = getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setInt(1, categoryId);
        try (ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Brand b = new Brand();
                b.setBrandId(rs.getInt("BrandId"));
                b.setBrandName(rs.getString("BrandName"));
                list.add(b);
            }
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    return list;
}

// Lọc brand theo Component Category
public List<Brand> getComponentBrandsByCategory(int categoryId) {
    List<Brand> list = new ArrayList<>();
    String sql = "SELECT b.BrandId, b.BrandName FROM Brand b " +
                 "JOIN BrandComponentCategory bc ON b.BrandId = bc.BrandId " +
                 "WHERE bc.CategoryId = ?";
    try (Connection conn = getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setInt(1, categoryId);
        try (ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Brand b = new Brand();
                b.setBrandId(rs.getInt("BrandId"));
                b.setBrandName(rs.getString("BrandName"));
                list.add(b);
            }
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    return list;
}

}

