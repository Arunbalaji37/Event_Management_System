package src.model;

import src.controller.DBConnection;

import java.sql.Connection;

public class ObjectCreation {
    private static DBConnection db=null;

    public static Connection getInstanceofDatabaseConnection(){
        if (db==null)
            db=DBConnection.Connection();
       return db.connect();
    }
    public static void closeDatabaseConnection(){
          if(db==null)
               return;
          db.closeConnection();        
    }
}
