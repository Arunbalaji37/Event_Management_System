package src.controller;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.DriverManager;

public class DBConnection {
    private String url = "jdbc:postgresql://localhost:5432/event_management_system";
    private String user = "postgres";
    private String pass = "1234arun12";
    private Connection conn=null;
    private DBConnection() {
        try {
            conn = DriverManager.getConnection(url, user, pass);
            if (conn!=null)
                System.out.println("Connection established successfully.");
            else
                System.out.println("connection failed");
        } catch (SQLException e) {
            System.out.println("Connection failed: " + e.getMessage());
        }
    }
    public static DBConnection Connection() {
        DBConnection db = new DBConnection();
        return db;
    }

    public Connection connect() {
        return conn;
    }

    public void closeConnection(){
        try{
            conn.close();
            System.out.println("Connection closed");
        }catch(SQLException e){
            System.out.println(e);
        }
    }
}
