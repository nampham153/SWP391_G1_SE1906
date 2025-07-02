package dao;

import context.DBContext;
import model.Account;
import model.Role;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AccountDAO extends DBContext {

public Account getAccount(String phone, String password) {
    try (Connection conn = getConnection()) {
        String query = "SELECT a.Phone, a.Password, a.RoleId, a.Status, a.IsVerified, r.RoleName " +
                       "FROM Account a INNER JOIN Role r ON a.RoleId = r.RoleId " +
                       "WHERE a.Phone = ? AND a.Password = ?";
        PreparedStatement stm = conn.prepareStatement(query);
        stm.setString(1, phone);
        stm.setString(2, password);
        ResultSet rs = stm.executeQuery();
        if (rs.next()) {
            Account account = new Account();
            account.setPhone(rs.getString("Phone"));
            account.setPassword(rs.getString("Password"));
            account.setRoleId(rs.getInt("RoleId"));
            account.setStatus(rs.getInt("Status"));
            account.setVerified(rs.getBoolean("IsVerified"));
            return account;
        }
    } catch (Exception ex) {
        Logger.getLogger(AccountDAO.class.getName()).log(Level.SEVERE, null, ex);
    }
    return null;
}


    // ✅ Hàm createAccount mặc định status = 1
    public boolean createAccount(String phone, String password, int roleId) {
        return createAccount(phone, password, roleId, 1);
    }

    // Hàm tạo tài khoản đầy đủ tham số
    public boolean createAccount(String phone, String password, int roleId, int status) {
        try (Connection conn = getConnection()) {
            String query = "INSERT INTO Account (Phone, Password, RoleId, Status) VALUES (?, ?, ?, ?)";
            PreparedStatement stm = conn.prepareStatement(query);
            stm.setString(1, phone);
            stm.setString(2, password);
            stm.setInt(3, roleId);
            stm.setInt(4, status);
            stm.executeUpdate();
            return true;
        } catch (Exception ex) {
            Logger.getLogger(AccountDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public void deleteAccount(String phone) {
        try (Connection conn = getConnection()) {
            String query = "DELETE FROM Account WHERE Phone = ?";
            PreparedStatement stm = conn.prepareStatement(query);
            stm.setString(1, phone);
            stm.executeUpdate();
        } catch (Exception ex) {
            Logger.getLogger(AccountDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public List<Account> getByRole(int roleId) {
        List<Account> list = new ArrayList<>();
        try (Connection conn = getConnection()) {
            String query = "SELECT a.Phone, a.Password, a.RoleId, r.RoleName FROM Account a INNER JOIN Role r ON a.RoleId = r.RoleId WHERE a.RoleId = ?";
            PreparedStatement stm = conn.prepareStatement(query);
            stm.setInt(1, roleId);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Account acc = new Account();
                acc.setPhone(rs.getString("Phone"));
                acc.setPassword(rs.getString("Password"));

                Role role = new Role();
                role.setRoleId(rs.getInt("RoleId"));
                role.setRoleName(rs.getString("RoleName"));
                acc.setStatus(roleId);

                list.add(acc);
            }
        } catch (Exception ex) {
            Logger.getLogger(AccountDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public boolean updateAccount(String phone, String newPassword, int newRoleId) {
        String sql = "UPDATE Account SET Password = ?, RoleId = ? WHERE Phone = ? AND Status = 1";
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, newPassword);
            ps.setInt(2, newRoleId);
            ps.setString(3, phone);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean restoreAccount(String phone) {
        String sql = "UPDATE Account SET Status = 1 WHERE Phone = ? AND Status = 0";
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, phone);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean isPhoneExist(String phone) {
        String sql = "SELECT Phone FROM Account WHERE Phone = ?";
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, phone);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    public boolean setAccountVerified(String phone) {
    String sql = "UPDATE Account SET IsVerified = TRUE WHERE Phone = ?";
    try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setString(1, phone);
        return ps.executeUpdate() > 0;
    } catch (Exception e) {
        e.printStackTrace();
        return false;
    }
}

public boolean isVerified(String phone) {
    String sql = "SELECT IsVerified FROM Account WHERE Phone = ?";
    try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setString(1, phone);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) return rs.getBoolean("IsVerified");
    } catch (Exception e) {
        e.printStackTrace();
    }
    return false;
}

}
