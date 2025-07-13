/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.*;
import model.AccountVerificationCode;
import context.DBContext;
/**
 *
 * @author namp0
 */
public class AccountVerificationDAO extends DBContext {

    public boolean saveVerificationCode(String phone, String code, Timestamp expireAt) {
        String sql = "INSERT INTO AccountVerificationCode (Phone, Code, ExpireAt) VALUES (?, ?, ?)";
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, phone);
            ps.setString(2, code);
            ps.setTimestamp(3, expireAt);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public AccountVerificationCode getVerificationCode(String phone) {
        String sql = "SELECT Code, ExpireAt FROM AccountVerificationCode WHERE Phone = ?";
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, phone);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new AccountVerificationCode(
                        phone,
                        rs.getString("Code"),
                        rs.getTimestamp("ExpireAt")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
