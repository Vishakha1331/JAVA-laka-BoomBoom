package tools;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    
    public static Connection connectDB(){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con= DriverManager.getConnection("jdbc:mysql://localhost:3306/javalaka", "root", "");
            
            return con;
        }
        catch (ClassNotFoundException | SQLException e){ System.out.println("database exception");}
        return null;
    }
}
