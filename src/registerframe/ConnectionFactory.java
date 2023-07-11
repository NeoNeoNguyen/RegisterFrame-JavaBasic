package registerframe;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {

    public static Connection getConnection() {
        Connection c = null;
        
final String url = "jdbc:mysql://localhost:3306/studentmanagement";
final String user = "root";
final String password = "";
        try {
            c = DriverManager.getConnection(url, user, password);
//            Class.forName("com.mysql.jdbc.Driver");
//            c = DriverManager.getConnection("jdbc:mysql://localhost:3306/" 
//                    + "StudentManagement?useUnicode=true&characterEncoding=UTF-8", "root", "");
//        } catch (ClassNotFoundException ex) {
//            System.out.println("ClassNotFoundException" + ex);
        } catch (SQLException e) {
            System.out.println("SQLException" + e);
        }
        return c;
    }
}
