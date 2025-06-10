package dao;

import context.DBContext;
import model.Account;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AccountDAO extends DBContext {

    public Account getAccount(String phone, String password) {
        try (Connection conn = getConnection()) {
            String query = "SELECT * FROM Account WHERE Phone = ? AND Password = ?";
            PreparedStatement stm = conn.prepareStatement(query);
            stm.setString(1, phone);
            stm.setString(2, password);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                Account account = new Account();
                account.setPhone(phone);
                account.setPassword(password);
                account.setRoleId(rs.getInt("RoleId"));
                return account;
            }
        } catch (Exception ex) {
            Logger.getLogger(AccountDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public boolean createAccount(String phone, String password, int roleId) {
        try (Connection conn = getConnection()) {
            String query = "INSERT INTO Account (Phone, Password, RoleId) VALUES (?, ?, ?)";
            PreparedStatement stm = conn.prepareStatement(query);
            stm.setString(1, phone);
            stm.setString(2, password);
            stm.setInt(3, roleId);
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
            String query = "SELECT Phone, Password, RoleId FROM Account WHERE RoleId = ?";
            PreparedStatement stm = conn.prepareStatement(query);
            stm.setInt(1, roleId);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Account acc = new Account();
                acc.setPhone(rs.getString("Phone"));
                acc.setPassword(rs.getString("Password"));
                acc.setRoleId(rs.getInt("RoleId"));
                list.add(acc);
            }
        } catch (Exception ex) {
            Logger.getLogger(AccountDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public static void main(String[] args) {
        AccountDAO dao = new AccountDAO();
        List<Account> accounts = dao.getByRole(2);
        System.out.println("Accounts with role 2:");
        for (Account acc : accounts) {
            System.out.println("Phone: " + acc.getPhone() + ", Role: " + acc.getRoleId());
        }
    }
}
