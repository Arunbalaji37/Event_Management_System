package src.controller;

import src.model.ObjectCreation;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdminDisplay {
     Connection conn = ObjectCreation.getInstanceofDatabaseConnection();
     public void ViewOrganizers() {
        try {
            String query = Queries.adminViewOrg;

            PreparedStatement pstmt = conn.prepareStatement(query);
            ResultSet rs = pstmt.executeQuery();
            System.out.println("+-----------+--------------+-------------------------+--------------+---------+");
            System.out.println("| Organizer name         | Email                   | Mobile Number | Role    |");
            System.out.println("+-----------+--------------+-------------------------+--------------+---------+");

            int organizerCount = 0;
            while (rs.next()) {
                organizerCount++;
                String name = rs.getString("name");
                String email = rs.getString("email");
                String mobileNumber = rs.getString("mobile_number");
                String role = rs.getString("role");

                System.out.printf("| %-12s | %-23s | %-12s | %-7s |\n",
                        name, email, mobileNumber, role);
            }

            System.out.println("+-----------+--------------+-------------------------+--------------+---------+");
            System.out.println("Total Organizers: " + organizerCount);

        } catch (SQLException e) {
            System.out.println("Organizer display failed: " + e.getMessage());
        }
    }

    public void ViewCustomers() {
        try {
            String query = Queries.adminViewCust;

            PreparedStatement pstmt = conn.prepareStatement(query);
            ResultSet rs = pstmt.executeQuery();
            System.out.println("+-----------+--------------+-------------------------+--------------+---------+");
            System.out.println("| Customer name         | Email                   | Mobile Number | Role    |");
            System.out.println("+-----------+--------------+-------------------------+--------------+---------+");
            int customerCount = 0;
            while (rs.next()) {
                customerCount++;
                String name = rs.getString("name");
                String email = rs.getString("email");
                String mobileNumber = rs.getString("mobile_number");
                String role = rs.getString("role");

                System.out.printf("| %-20s | %-23s | %-12s | %-7s |\n",
                        name, email, mobileNumber, role);
            }

            System.out.println("+-----------+--------------+-------------------------+--------------+---------+");
            System.out.println("Total Customers: " + customerCount);

        } catch (SQLException e) {
            System.out.println("Customer display failed: " + e.getMessage());
        }
    }

    public void ViewBookingDetails() {
        try {
            String query = Queries.adminViewBook;

            PreparedStatement pstmt = conn.prepareStatement(query);
            ResultSet rs = pstmt.executeQuery();
            System.out.println("+------------+---------------------+---------------------+------------+------------+----------------+");
            System.out.println("| Booking ID | Customer Name       | Venue Name          | Event Date | Event Cost | Event Name     |");
            System.out.println("+------------+---------------------+---------------------+------------+------------+----------------+");
            while (rs.next()) {
                int bookingId = rs.getInt("booking_id");
                String customerName = rs.getString("customer_name");
                String venueName = rs.getString("venue_name");
                String eventDate = rs.getString("event_date");
                double eventCost = rs.getDouble("event_cost");
                String eventName = rs.getString("event_name");

                System.out.printf("| %-10d | %-19s | %-19s | %-11s | %-10.2f | %-14s |\n",
                bookingId, customerName, venueName, eventDate, eventCost, eventName);
            }

            System.out.println("+------------+---------------------+---------------------+------------+------------+----------------+");

        } catch (SQLException e) {
            System.out.println("Booking details display failed: " + e.getMessage());
        }
    }


    public void ViewVenue() {
        try {
            String query = Queries.adminViewVenue;
            PreparedStatement pstmt = conn.prepareStatement(query);
            ResultSet rs = pstmt.executeQuery();
            System.out.println("+-----------+--------------+-------------------------+--------------+---------+");
            System.out.printf(" %-20s %-15s %-15s %-30s  %-15s%n",
                    "Venue Name", "Venue Place", "Contact", "Email", "Organizer ID");
            System.out.println("+-----------+--------------+-------------------------+--------------+---------+");
            while (rs.next()) {
                String venueName = rs.getString("venue_name");
                String venuePlace = rs.getString("venue_place");
                String venueContact = rs.getString("venue_contact");
                String venueEmail = rs.getString("venue_email");
                int capacity = rs.getInt("capacity");
                int organizerId = rs.getInt("organizer_id");

                System.out.printf("%-20s %-15s %-15s %-30s %-15d%n",
                        venueName, venuePlace, venueContact, venueEmail, organizerId);
            }
            System.out.println("+-----------+--------------+-------------------------+--------------+---------+");
        } catch (SQLException e) {
            System.out.println("Venue display failed: " + e.getMessage());
        }
    }
}
