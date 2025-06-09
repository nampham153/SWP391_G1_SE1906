package dao;

import context.DBContext;
import model.Staff;

import java.sql.*;
import java.util.*;

public class StaffDAO {
    private Connection conn;

    public StaffDAO(Connection conn) {
        this.conn = conn;
    }

    public boolean isAccountIdValid(String accountId) throws SQLException {
        // 1. Kiểm tra accountId tồn tại trong customer
        String sqlCustomer = "SELECT COUNT(*) FROM customer WHERE accountId = ?";
        try (PreparedStatement stmtCustomer = conn.prepareStatement(sqlCustomer)) {
            stmtCustomer.setString(1, accountId);
            try (ResultSet rs = stmtCustomer.executeQuery()) {
                rs.next();
                if (rs.getInt(1) == 0) return false;
            }
        }

        String sqlAccount = "SELECT COUNT(*) FROM account WHERE accountId = ?";
        try (PreparedStatement stmtAccount = conn.prepareStatement(sqlAccount)) {
            stmtAccount.setString(1, accountId);
            try (ResultSet rs = stmtAccount.executeQuery()) {
                rs.next();
                if (rs.getInt(1) == 0) return false;
            }
        }

        String sqlRole = "SELECT role FROM account WHERE accountId = ?";
        try (PreparedStatement stmtRole = conn.prepareStatement(sqlRole)) {
            stmtRole.setString(1, accountId);
            try (ResultSet rs = stmtRole.executeQuery()) {
                if (rs.next()) {
                    int role = rs.getInt("role");
                    return role == 2;
                }
            }
        }
        return false;
    }

    public List<Staff> getAll(String keyword, String statusFilter) throws SQLException {
    List<Staff> list = new ArrayList<>();
    String sql = "SELECT * FROM staff WHERE staffName LIKE ?";
    if (!"all".equals(statusFilter)) {
        sql += " AND status = ?";
    }

    PreparedStatement stmt = conn.prepareStatement(sql);
    stmt.setString(1, "%" + keyword + "%");

    if (!"all".equals(statusFilter)) {
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
        public List<String> getCustomerIdsWithStaffRole() throws SQLException {
        List<String> list = new ArrayList<>();
        String sql = "SELECT c.customerId FROM customer c " +
                     "JOIN account a ON c.customerId = a.accountId " +
                     "WHERE a.role = 2 AND a.status = true";
        PreparedStatement stmt = conn.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            list.add(rs.getString("customerId"));
        }
        return list;
    }
    public static void main(String[] args) {
        try {
            Connection conn = new DBContext().getConnection();
            StaffDAO dao = new StaffDAO(conn);

            List<Staff> list = dao.getAll("", "all");
            for (Staff s : list) {
                System.out.println(s.getStaffId() + " - " + s.getStaffName() + " - Status: " + (s.isStatus() ? "Hoạt động" : "Ngưng hoạt động"));
            }

            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
