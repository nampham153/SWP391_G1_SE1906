/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

/**
 *
 * @author tuananh
 */
public class DAOWrapper {
    public static AccountDAO accountDAO = new AccountDAO();
    public static ProductCategoryDAO productCategoryDAO = new ProductCategoryDAO();
    public static ReviewDAO reviewDAO = new ReviewDAO();
    public static CustomerDAO customerDAO = new CustomerDAO();
    public static RoleDAO roleDAO = new RoleDAO();
}
