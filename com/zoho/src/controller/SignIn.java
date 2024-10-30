package src.controller;

import src.model.ObjectCreation;
import src.model.User;
import java.io.Console;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SignIn {
    Connection conn= ObjectCreation.getInstanceofDatabaseConnection();
    public User userSignIn(String username) {
        String role;
        try {
            String sql = Queries.SelectUserCred;
            PreparedStatement st = conn.prepareStatement(sql);
            st.setString(1, username);
            ResultSet resultSet = st.executeQuery();
            if (resultSet.next()) {
                int id = resultSet.getInt("userid");
                String storedPassword = resultSet.getString("password").trim();
                String inputPassword;
                while (true) {
                    Console console = System.console();
                    if (console == null) {	
                        System.out.println("No console available. Please run in a terminal.");
                        return null;
                    }
                    char[] passwordArray = console.readPassword("Enter your password: ");
                    inputPassword = new String(passwordArray).trim();
                    if (!storedPassword.equals(inputPassword)) {
                        System.out.println("Incorrect password. Please try again.");
                    } else {
                        String query2 = Queries.SelectUserRole;
                        PreparedStatement ps = conn.prepareStatement(query2);
                        ps.setInt(1, id);
                        ResultSet rs = ps.executeQuery();
                        if (rs.next()) {
                            role = rs.getString("role");
                        } else {
                            role = "admin";
                        }
                        String query3 = Queries.SelectUserId;
                        ps = conn.prepareStatement(query3);
                        ps.setInt(1, id);
                        rs = ps.executeQuery();
                        if (rs.next()) {
                            User user = new User(rs.getString("name"), rs.getString("email"), rs.getString("mobile_number"), id, role);
                            System.out.println("Login Successful! ");
                            return user;
                        }
                        break;
                    }
                }
            } 
        } catch (SQLException e) {
            System.out.println("Error during login: " + e.getMessage());
        }
        return null;
    }
}
