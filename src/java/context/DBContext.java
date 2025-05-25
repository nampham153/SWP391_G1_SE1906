package context;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DBContext {

    private final String serverName = "localhost";
    private final String dbName = "swp391";
    private final String portNumber = "3306";
    private final String userID = "root";
    private final String password = "123456";
    protected Connection connection;

    public DBContext() {
        try {
            String user = "root";
            String pass = "123456";
            String url = "jdbc:mysql://localhost:3306/swp391?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(url, user, pass);
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(DBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public Connection getConnection() throws ClassNotFoundException, SQLException {
        // Load driver
        Class.forName("com.mysql.cj.jdbc.Driver");
        // Tạo chuỗi kết nối
        String url = "jdbc:mysql://" + serverName + ":" + portNumber + "/" + dbName
                + "?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
        // Tạo kết nối
        if(connection.isClosed())
            return DriverManager.getConnection(url, userID, password);
        return connection;
        //return DriverManager.getConnection(url, userID, password);
    }

    /* public static void main(String[] args) {
        try {
            Connection conn = new DBContext().getConnection();
            System.out.println("Kết nối thành công!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    } */

    
    
}
