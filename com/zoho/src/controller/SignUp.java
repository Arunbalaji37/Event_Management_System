package src.controller;

import src.model.ObjectCreation;
import src.model.User;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SignUp {
    Connection conn= ObjectCreation.getInstanceofDatabaseConnection();

    public SignUp(User u, String username, String password){
        try{
            Statement st = conn.createStatement();
            String query = "Insert into users (name,email,mobile_number) values (' "+u.getName()+" ' , ' "+u.getEmail()+" ' , ' "+u.getPhone()+" ');";
            int row = st.executeUpdate(query);
            ResultSet rs = st.executeQuery("SELECT LASTVAL()");
            if(rs.next()) {
                int userid=rs.getInt(1);
                String query2 = "Insert into usercredential (username,password,userid) values ('"+username+"' ,'"+password+"','"+userid+"');";
                int row2 = st.executeUpdate(query2);
                String query3 = "Insert into roles (userid,role)  values ("+userid+",'"+u.getRole()+"');";
                int row3 = st.executeUpdate(query3);
                System.out.println("Account created successfully");
            }
        }catch(SQLException e){
            System.out.println("signup failed : "+e.getMessage());
        }
    }
}
