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
import model.Role;

/**
 *
 * @author tuananh
 */
public class RoleDAO extends DBContext {
    public Role getRoleByName(String roleName)
    {
        try {
            String query = "SELECT * FROM Role WHERE RoleName = ?";
            PreparedStatement stm = connection.prepareStatement(query);
            stm.setString(1, roleName);
            ResultSet rs = stm.executeQuery();
            if(rs.next())
            {
                Role role = new Role();
                role.setRoleId(rs.getInt("RoleId"));
                role.setRoleName(rs.getString("RoleName"));
                return role;
            }
        } catch (SQLException ex) {
            Logger.getLogger(RoleDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
