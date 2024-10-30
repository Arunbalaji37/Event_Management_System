package src.controller;

import src.model.ObjectCreation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Payment_details {
     private Connection conn = ObjectCreation.getInstanceofDatabaseConnection();
     private Scanner scanner = new Scanner(System.in);

     public void viewPaymentDetails(int bookingId) {
          String query = Queries.PaymentDetails;
          try (PreparedStatement pstmt = conn.prepareStatement(query)) {
               pstmt.setInt(1, bookingId);
               try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                         System.out.println("Payment Details for Booking ID: " + bookingId);
                         System.out.println("Advance Payment: " + rs.getDouble("advance_payment"));
                         System.out.println("Available Payment: " + rs.getDouble("available_payment"));
                    } else {
                         System.out.println("No payment details found for Booking ID: " + bookingId);
                    }
               }
          } catch (SQLException e) {
               e.printStackTrace();
          }
     }
}

