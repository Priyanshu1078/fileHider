package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class myConnections {
    public static Connection connection;
    public static Connection getConnection(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection= DriverManager.getConnection("jdbc:mysql://localhost:3306/fileHider?useSSL=false","root","py140104");
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
        System.out.println("DB Connected");
        return connection;
    }
    public static void closeConnection(){
        if(connection!=null){
            try{
                connection.close();
            }catch (SQLException ex){
                ex.printStackTrace();
            }
        }
    }

}
