package src.controller;

import src.model.ObjectCreation;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomerDisplay {
    Connection conn = ObjectCreation.getInstanceofDatabaseConnection();
    public void viewProfile(int userId) {
        String query = Queries.CustProfile;
        try {
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, userId);
            ResultSet resultSet = pstmt.executeQuery();
            if (resultSet.next()) {
                int id = resultSet.getInt("userid");
                String name = resultSet.getString("name");
                String email = resultSet.getString("email");
                String mobileNumber = resultSet.getString("mobile_number");
                String role = resultSet.getString("role");

                System.out.println("=========== User Profile ===========");
                System.out.printf("%-15s: %d\n", "User ID", id);
                System.out.printf("%-15s: %s\n", "Name", name);
                System.out.printf("%-15s: %s\n", "Role", role);
                System.out.printf("%-15s: %s\n", "Email", email);
                System.out.printf("%-15s: %s\n", "Mobile Number", mobileNumber);
                System.out.println("====================================");
            } else {
                System.out.println("No user found with ID: " + userId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
