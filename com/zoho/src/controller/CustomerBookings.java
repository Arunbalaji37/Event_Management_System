package src.controller;

import src.model.ObjectCreation;
import src.model.User;
import java.sql.*;


public class CustomerBookings {
Connection conn = ObjectCreation.getInstanceofDatabaseConnection();
    public void viewCustomerBookings(User u) {
        String query = Queries.ViewCustBook;

        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, u.getId());
            ResultSet rs = pstmt.executeQuery();

            System.out.println("=========== Customer Bookings ===========");
            while (rs.next()) {
                String venueName = rs.getString("venue_name");
                String venuePlace = rs.getString("venue_place");
                String eventName = rs.getString("event_name");
                Date eventDate = rs.getDate("event_date");
                Time eventTime = rs.getTime("event_time");
                double totalAmount = rs.getDouble("total_amount");

                System.out.println("------------------------------------");
                System.out.printf("%-15s: %s\n", "Venue", venueName);
                System.out.printf("%-15s: %s\n", "Location", venuePlace);
                System.out.printf("%-15s: %s\n", "Event", eventName);
                System.out.printf("%-15s: %s %s\n", "Date & Time", eventDate, eventTime);
                System.out.printf("%-15s: %.2f\n", "Total Amount", totalAmount);
                System.out.println("------------------------------------");
            }
            System.out.println("====================================");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void CustomerUpcomingBookings(User u) {
        String query = Queries.viewCustUpBook;

        Date currentDate = new Date(System.currentTimeMillis());

        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, u.getId());
            pstmt.setDate(2, new java.sql.Date(currentDate.getTime())); 

            ResultSet rs = pstmt.executeQuery();

            System.out.println("=========== Upcoming Customer Bookings ===========");
            while (rs.next()) {
                String venueName = rs.getString("venue_name");
                String venuePlace = rs.getString("venue_place");
                String eventName = rs.getString("event_name");
                Date eventDate = rs.getDate("event_date");
                Time eventTime = rs.getTime("event_time");
                double totalAmount = rs.getDouble("total_amount");

                System.out.println("------------------------------------");
                System.out.printf("%-15s: %s\n", "Venue", venueName);
                System.out.printf("%-15s: %s\n", "Location", venuePlace);
                System.out.printf("%-15s: %s\n", "Event", eventName);
                System.out.printf("%-15s: %s %s\n", "Date & Time", eventDate, eventTime);
                System.out.printf("%-15s: %.2f\n", "Total Amount", totalAmount);
                System.out.println("------------------------------------");
            }
            System.out.println("====================================");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
