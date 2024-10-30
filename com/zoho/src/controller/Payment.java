package src.controller;

import src.model.ObjectCreation;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

abstract class PaymentMethod {
     protected Connection conn = ObjectCreation.getInstanceofDatabaseConnection();
     protected String paymentMethod;

     public PaymentMethod(String paymentMethod) {
          this.paymentMethod = paymentMethod;
     }

     public abstract void processPayment(int bookingId, double amountPaid);
}

class AdvancePayment extends PaymentMethod implements inter{
     public AdvancePayment(String paymentMethod) {
          super(paymentMethod);
     }

     @Override
     public void processPayment(int bookingId, double amountPaid) {
          String queryPayment = Queries.AdvInserPaymentProcess;
          String updateBooking = Queries.AdvUpdatePaymentProcess;

          try {
               PreparedStatement pstmtPayment = conn.prepareStatement(queryPayment);
               pstmtPayment.setInt(1, bookingId);
               pstmtPayment.setString(2, paymentMethod);
               pstmtPayment.setDouble(3, amountPaid);
               pstmtPayment.setString(4, "Pending");
               pstmtPayment.executeUpdate();

               PreparedStatement pstmtBooking = conn.prepareStatement(updateBooking);
               pstmtBooking.setDouble(1, amountPaid);
               pstmtBooking.setDouble(2, amountPaid);
               pstmtBooking.setInt(3, bookingId);
               pstmtBooking.executeUpdate();

               System.out.println("Advance payment recorded successfully!");
          } catch (SQLException e) {
               System.out.println("Advance payment failed: " + e);
          }
     }
}

class FullPayment extends PaymentMethod implements inter{
     public FullPayment(String paymentMethod) {
          super(paymentMethod);
     }

     @Override
     public void processPayment(int bookingId, double amountPaid) {
          String queryPayment = Queries.FullInserPaymentProcess;
          String updateBooking = Queries.FullUpdatePaymentProcess;

          try {
               PreparedStatement pstmtPayment = conn.prepareStatement(queryPayment);
               pstmtPayment.setInt(1, bookingId);
               pstmtPayment.setString(2, paymentMethod);
               pstmtPayment.setDouble(3, amountPaid);
               pstmtPayment.setString(4, "Success");
               pstmtPayment.executeUpdate();

               PreparedStatement pstmtBooking = conn.prepareStatement(updateBooking);
               pstmtBooking.setInt(1, bookingId);
               pstmtBooking.executeUpdate();

               System.out.println("Full payment recorded successfully!");
          } catch (SQLException e) {
               System.out.println("Full payment failed: " + e);
          }
     }
}

public class Payment {
     public void handlePayment(int bookingId, double amountPaid, String paymentMethod, boolean isFullPayment) {
          PaymentMethod payment = isFullPayment ? new FullPayment(paymentMethod) : new AdvancePayment(paymentMethod);
          payment.processPayment(bookingId, amountPaid);
     }
}

