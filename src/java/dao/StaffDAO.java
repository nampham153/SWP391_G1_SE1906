/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

/**
 *
 * @author namp0
 */
import context.DBContext;
import model.Staff;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class StaffDAO {

    public List<Staff> getAllStaff() {
        List<Staff> list = new ArrayList<>();
        String query = "SELECT * FROM Staff";

        try (Connection conn = new DBContext().getConnection(); PreparedStatement ps = conn.prepareStatement(query); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Staff s = extractStaffFromResultSet(rs);
                list.add(s);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    // Get staff by ID
    public Staff getStaffById(String id) {
        String query = "SELECT * FROM Staff WHERE StaffId = ?";

        try (Connection conn = new DBContext().getConnection(); PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return extractStaffFromResultSet(rs);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    // Insert staff
    public boolean insertStaff(Staff s) {
        String query = "INSERT INTO Staff ("
                + "StaffId,"
                + " StaffName,"
                + " StaffTitle,"
                + " StaffAddress,"
                + " StaffBirthDate,"
                + " StaffGender,"
                + " SupervisorId,"
                + " DepartmentId,"
                + " Status) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = new DBContext().getConnection(); PreparedStatement ps = conn.prepareStatement(query)) {

            setStaffPreparedStatement(ps, s);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    // Update staff
    public boolean updateStaff(Staff s) {
        String query = "UPDATE Staff SET "
                + "StaffName = ?, "
                + "StaffTitle = ?, "
                + "StaffAddress = ?, "
                + "StaffBirthDate = ?, "
                + "StaffGender = ?, "
                + "SupervisorId = ?, "
                + "DepartmentId = ?, "
                + "Status = ? "
                + "WHERE StaffId = ?";

        try (Connection conn = new DBContext().getConnection(); PreparedStatement ps = conn.prepareStatement(query)) {

            // Set parameters in order
            ps.setString(1, s.getStaffName());
            ps.setString(2, s.getStaffTitle());
            ps.setString(3, s.getStaffAddress());
            ps.setDate(4, new java.sql.Date(s.getStaffBirthDate().getTime()));
            ps.setBoolean(5, s.isStaffGender());
            ps.setString(6, s.getSupervisorId());
            ps.setInt(7, s.getDepartmentId());
            ps.setBoolean(8, s.isStatus());
            ps.setString(9, s.getStaffId());

            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    // Delete staff
    public boolean deleteStaff(String id) {
        String query = "DELETE FROM Staff WHERE StaffId = ?";

        try (Connection conn = new DBContext().getConnection(); PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, id);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

   
    public List<Staff> searchStaffByName(String name) {
        List<Staff> list = new ArrayList<>();
        String query = "SELECT * FROM Staff WHERE StaffName LIKE ?";

        try (Connection conn = new DBContext().getConnection(); PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, "%" + name + "%");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Staff s = extractStaffFromResultSet(rs);
                list.add(s);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    private Staff extractStaffFromResultSet(ResultSet rs) throws SQLException {
        Staff s = new Staff();
        s.setStaffId(rs.getString("StaffId"));
        s.setStaffName(rs.getString("StaffName"));
        s.setStaffTitle(rs.getString("StaffTitle"));
        s.setStaffAddress(rs.getString("StaffAddress"));
        s.setStaffBirthDate(rs.getDate("StaffBirthDate"));
        s.setStaffGender(rs.getBoolean("StaffGender"));
        s.setSupervisorId(rs.getString("SupervisorId"));
        s.setDepartmentId(rs.getInt("DepartmentId"));
        s.setStatus(rs.getBoolean("Status"));
        return s;
    }

    private void setStaffPreparedStatement(PreparedStatement ps, Staff s) throws SQLException {
        ps.setString(1, s.getStaffId());
        ps.setString(2, s.getStaffName());
        ps.setString(3, s.getStaffTitle());
        ps.setString(4, s.getStaffAddress());
        ps.setDate(5, new java.sql.Date(s.getStaffBirthDate().getTime()));
        ps.setBoolean(6, s.isStaffGender());
        ps.setString(7, s.getSupervisorId());
        ps.setInt(8, s.getDepartmentId());
        ps.setBoolean(9, s.isStatus());
    }

    public static void main(String[] args) {
        StaffDAO dao = new StaffDAO();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        try {

            // 🔹 Test getAll
            List<Staff> staffList = dao.getAllStaff();
            for (Staff s : staffList) {
                System.out.println(s.getStaffId() + " - " + s.getStaffName());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
