/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package context;

import dao.AccountDAO;
import dao.CustomerDAO;
import dao.RoleDAO;

/**
 *
 * @author tuananh
 */
public class DBWrapper {

    public static DBContext db = new DBContext();
    CustomerDAO customerDAO = new CustomerDAO();
    AccountDAO accountDAO = new AccountDAO();
    RoleDAO roleDAO = new RoleDAO();

}
