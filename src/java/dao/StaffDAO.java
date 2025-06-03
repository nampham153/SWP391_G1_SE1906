package dao;

import model.Staff;

import java.sql.*;
import java.util.*;

public class StaffDAO {
    private Connection conn;

    public StaffDAO(Connection conn) {
        this.conn = conn;
    }

    public List<Staff> getAll(String keyword, String statusFilter) throws SQLException {
        List<Staff> list = new ArrayList<>();
        String sql = "SELECT * FROM staff WHERE staffName LIKE ?";
        if (!statusFilter.equals("all")) {
            sql += " AND status = ?";
        }

        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, "%" + keyword + "%");

        if (!statusFilter.equals("all")) {
            stmt.setBoolean(2, Boolean.parseBoolean(statusFilter));
        }

        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            Staff s = new Staff(
                rs.getString("staffId"),
                rs.getString("staffName"),
                rs.getString("staffTitle"),
                rs.getString("staffAddress"),
                rs.getDate("staffBirthDate"),
                rs.getBoolean("staffGender"),
                rs.getString("supervisorId"),
                rs.getInt("departmentId"),
                rs.getBoolean("status")
            );
            list.add(s);
        }
        return list;
    }

    public Staff getById(String id) throws SQLException {
        String sql = "SELECT * FROM staff WHERE staffId = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, id);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            return new Staff(
                rs.getString("staffId"),
                rs.getString("staffName"),
                rs.getString("staffTitle"),
                rs.getString("staffAddress"),
                rs.getDate("staffBirthDate"),
                rs.getBoolean("staffGender"),
                rs.getString("supervisorId"),
                rs.getInt("departmentId"),
                rs.getBoolean("status")
            );
        }
        return null;
    }

    public void insert(Staff s) throws SQLException {
        String sql = "INSERT INTO staff (staffId, staffName, staffTitle, staffAddress, staffBirthDate, staffGender, supervisorId, departmentId, status) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, s.getStaffId());
        stmt.setString(2, s.getStaffName());
        stmt.setString(3, s.getStaffTitle());
        stmt.setString(4, s.getStaffAddress());
        stmt.setDate(5, new java.sql.Date(s.getStaffBirthDate().getTime()));
        stmt.setBoolean(6, s.isStaffGender());

        // SupervisorId có thể null hoặc rỗng, không kiểm tra tồn tại nữa
        if (s.getSupervisorId() == null || s.getSupervisorId().trim().isEmpty()) {
            stmt.setNull(7, java.sql.Types.VARCHAR);
        } else {
            stmt.setString(7, s.getSupervisorId());
        }

        stmt.setInt(8, s.getDepartmentId());
        stmt.setBoolean(9, s.isStatus());
        stmt.executeUpdate();
    }

    public void update(Staff s) throws SQLException {
        String sql = "UPDATE staff SET staffName=?, staffTitle=?, staffAddress=?, staffBirthDate=?, staffGender=?, supervisorId=?, departmentId=?, status=? WHERE staffId=?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, s.getStaffName());
        stmt.setString(2, s.getStaffTitle());
        stmt.setString(3, s.getStaffAddress());
        stmt.setDate(4, new java.sql.Date(s.getStaffBirthDate().getTime()));
        stmt.setBoolean(5, s.isStaffGender());

        // SupervisorId có thể null hoặc rỗng, không kiểm tra tồn tại nữa
        if (s.getSupervisorId() == null || s.getSupervisorId().trim().isEmpty()) {
            stmt.setNull(6, java.sql.Types.VARCHAR);
        } else {
            stmt.setString(6, s.getSupervisorId());
        }

        stmt.setInt(7, s.getDepartmentId());
        stmt.setBoolean(8, s.isStatus());
        stmt.setString(9, s.getStaffId());
        stmt.executeUpdate();
    }

    public void disableStaff(String id) throws SQLException {
        String sql = "UPDATE staff SET status = false WHERE staffId = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, id);
        stmt.executeUpdate();
    }

    public boolean hasCustomerOrAccount(String staffId) throws SQLException {
        String sqlCustomer = "SELECT COUNT(*) FROM customer WHERE staffId = ?";
        String sqlAccount = "SELECT COUNT(*) FROM account WHERE staffId = ?";

        PreparedStatement stmtC = conn.prepareStatement(sqlCustomer);
        stmtC.setString(1, staffId);
        ResultSet rsC = stmtC.executeQuery();
        rsC.next();
        int customerCount = rsC.getInt(1);

        PreparedStatement stmtA = conn.prepareStatement(sqlAccount);
        stmtA.setString(1, staffId);
        ResultSet rsA = stmtA.executeQuery();
        rsA.next();
        int accountCount = rsA.getInt(1);

        return customerCount > 0 || accountCount > 0;
    }
}
