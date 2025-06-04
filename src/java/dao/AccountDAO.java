/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import context.DBContext;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Account;

/**
 *
 * @author tuananh
 */
public class AccountDAO extends DBContext {
    public Account getAccount(String phone, String password)
    {
        try {
            String query = "SELECT * FROM Account WHERE Phone = ? AND Password = ?";
            PreparedStatement stm = connection.prepareStatement(query);
            stm.setString(1, phone);
            stm.setString(2, password);
            ResultSet rs = stm.executeQuery();
            if(rs.next())
            {
                Account account = new Account();
                account.setPhone(phone);
                account.setPassword(password);
                account.setRoleId(rs.getInt("RoleId"));
                return account;
            }
        } catch (SQLException ex) {
            Logger.getLogger(AccountDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public boolean createAccount(String phone, String password, int roleId)
    {
        try {
            String query = "INSERT INTO Account "
                    + "(Phone, Password, RoleId) "
                    + "VALUES (?, ?, ?)";
            PreparedStatement stm = connection.prepareStatement(query);
            stm.setString(1, phone);
            stm.setString(2, password);
            stm.setInt(3, roleId);
            stm.executeUpdate();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(AccountDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    public void deleteAccount(String phone)
    {
        try {
            String query = "DELETE FROM Account WHERE Phone = ?";
            PreparedStatement stm = connection.prepareStatement(query);
            stm.setString(1, phone);
            stm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(AccountDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
