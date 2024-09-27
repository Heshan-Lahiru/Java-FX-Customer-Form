package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class dbconnection {
private static dbconnection instance;
Connection connection;
    private dbconnection() throws SQLException{

          connection=  DriverManager.getConnection("jdbc:mysql://localhost:3306/thogakade", "root", "root");

    }

    public static dbconnection getInstance() throws SQLException {

        return instance==null?instance=new dbconnection():instance;
    }
    public Connection getConnection(){
        return connection;
    }
}
