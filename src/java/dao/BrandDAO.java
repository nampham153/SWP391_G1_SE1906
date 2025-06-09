/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;
import context.DBContext;
import model.Brand;
import java.sql.*;
import java.util.*;
/**
 *
 * @author namp0
 */
public class BrandDAO extends DBContext {

    public List<Brand> getAllBrands() {
        List<Brand> list = new ArrayList<>();
        String query = "SELECT * FROM Brand";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(query);
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

    public Brand getBrandById(int id) {
        String query = "SELECT * FROM Brand WHERE BrandId = ?";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new Brand(rs.getInt("BrandId"), rs.getString("BrandName"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public boolean insertBrand(Brand b) {
        String query = "INSERT INTO Brand (BrandName) VALUES (?)";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, b.getBrandName());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean updateBrand(Brand b) {
        String query = "UPDATE Brand SET BrandName = ? WHERE BrandId = ?";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, b.getBrandName());
            ps.setInt(2, b.getBrandId());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean deleteBrand(int id) {
        String query = "DELETE FROM Brand WHERE BrandId = ?";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
}