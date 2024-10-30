package src.view;

import src.controller.*;
import src.model.ObjectCreation;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import src.model.User;
import java.util.Scanner;

public class customer {
     Scanner s = new Scanner(System.in);
     Connection conn = ObjectCreation.getInstanceofDatabaseConnection();

     public void customer(User u) {
          System.out.println("\nWelcome Customer !! " + u.getName());
          while (true) {
               System.out.println("Enter your choice : \n");
               System.out.println("0. Logout");
               System.out.println("1. View profile");
               System.out.println("2. Book Event");
               System.out.println("3. Payment");
               System.out.println("4. View bookings");
               int choice = s.nextInt();
               Booking booking = new Booking();
               Payment_details paymentDetails = new Payment_details();
               Payment paymentController = new Payment(); 
               int bookingId = 0;

               switch (choice) {
                    case 0:
                         return;
                    case 1:
                         CustomerDisplay d = new CustomerDisplay();
                         d.viewProfile(u.getId());
                         break;
                    case 2:
                         bookingId = booking.bookPartyHall(u.getId());
                         if (bookingId != -1) {
                              paymentDetails.viewPaymentDetails(bookingId);
                         }
                         break;
                    case 3:
                         double amount = 0;
                         try {
                              amount = displayPendingPaymentsForUser(u.getId());
                         } catch (SQLException e) {
                              System.out.println("Error fetching pending payments: " + e.getMessage());
                         }
                         System.out.println("Are you going to pay the amount now (yes/no)?");
                         s.nextLine(); 
                         String paymentChoice = s.nextLine().toLowerCase();
                         if (paymentChoice.equals("yes")) {
                              System.out.println("Enter Booking ID to make payment:");
                              bookingId = s.nextInt();
                              System.out.println("Enter payment method (e.g., Cash, Credit Card, UPI):");
                              String paymentMethod = s.next();
                              paymentController.handlePayment(bookingId, amount, paymentMethod, true);
                         }
                         break;
                    case 4:
                         System.out.println("1. View all bookings");
                         System.out.println("2. View upcoming bookings");
                         int choice1 = s.nextInt();
                         CustomerBookings custbook = new CustomerBookings();
                         switch (choice1) {
                              case 1:
                                   custbook.viewCustomerBookings(u);
                                   break;
                              case 2:
                                   custbook.CustomerUpcomingBookings(u);
                                   break;
                         }
               } 
          } 
     }
     
     public double displayPendingPaymentsForUser(int userId) throws SQLException {
          double availablePayment = 0;
          String query = Queries.PendingAmount;
        
          try (PreparedStatement pstmt = conn.prepareStatement(query)) {
               pstmt.setInt(1, userId);
               try (ResultSet rs = pstmt.executeQuery()) {
                    System.out.println("Pending Payments for User ID " + userId + ":");
                    boolean hasPendingPayments = false;
                    while (rs.next()) {
                         hasPendingPayments = true;
                         int bookingId = rs.getInt("booking_id");
                         availablePayment = rs.getDouble("available_payment");
                         System.out.println("Booking ID: " + bookingId + ", Available Payment: " + availablePayment);
                    }
                    if (!hasPendingPayments) {
                         System.out.println("No pending payments for this user.");
                    }
               }
          }
          return availablePayment;
     }
}

