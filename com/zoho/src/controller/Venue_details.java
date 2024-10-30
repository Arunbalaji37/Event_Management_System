package src.controller;

import src.model.Event;
import src.model.ObjectCreation;
import src.model.Venue;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Venue_details {
    Connection conn = ObjectCreation.getInstanceofDatabaseConnection();
    public void addVenue(Venue v, int id) {
        try {
            String query = Queries.InserVenue;
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, v.getVenue_name());
            pstmt.setString(2, v.getVenue_place());
            pstmt.setString(3, v.getVenue_contact());
            pstmt.setString(4, v.getVenue_email());
            pstmt.setInt(5, v.getVenue_capacity());
            pstmt.setString(6, v.getVenue_ac());
            pstmt.setInt(7, id);
            pstmt.executeUpdate();
            System.out.println("Venue added successfully!!");
        } catch (SQLException e) {
            System.out.println("Addition failed: " + e.getMessage());
        }
    }

    public void addEvent(Event ev, String venueEmail, int organizerId) {
        int venueId = 0;
        try {
            String strquery = Queries.SelectEventForInsert;
            PreparedStatement pstmt1 = conn.prepareStatement(strquery);
            pstmt1.setInt(1, organizerId);
            ResultSet rs1 = pstmt1.executeQuery();
            boolean venueFound = false;
            while (rs1.next()) {
                String email = rs1.getString("venue_email");
                venueId = rs1.getInt("id");
                if (email.equals(venueEmail)) {
                    venueFound = true;
                    break;
                }
            }
            if (!venueFound) {
                System.out.println("No venue found with the provided email for the given organizer.");
                return;
            }
            String insertEventSQL = Queries.InserEvent;
            PreparedStatement pstmt = conn.prepareStatement(insertEventSQL);
            pstmt.setInt(1, venueId);
            pstmt.setString(2, ev.getEventName());
            pstmt.setDouble(3, ev.getEventCost());
            pstmt.executeUpdate();
            System.out.println("Event added successfully!");

        } catch (SQLException e) {
            System.out.println("Event addition failed: " + e.getMessage());
        }
    }

    public void addFood(String foodName, double foodCost, String venueEmail, int organizerId) {
        int venueId = 0;
        try {
            String strquery = Queries.SelectFoodForInset;
            PreparedStatement pstmt1 = conn.prepareStatement(strquery);
            pstmt1.setInt(1, organizerId);
            ResultSet rs1 = pstmt1.executeQuery();
            boolean venueFound = false;
            while (rs1.next()) {
                String email = rs1.getString("venue_email");
                venueId = rs1.getInt("id");
                if (email.equals(venueEmail)) {
                    venueFound = true;
                    break;
                }
            }
            if (!venueFound) {
                System.out.println("No venue found with the provided email for the given organizer.");
                return;
            }
            String insertFoodSQL = Queries.InsertFood;
            PreparedStatement pstmt = conn.prepareStatement(insertFoodSQL);
            pstmt.setInt(1, venueId);
            pstmt.setString(2, foodName);
            pstmt.setDouble(3, foodCost);
            pstmt.executeUpdate();
            System.out.println("Food added successfully!");
        } catch (SQLException e) {
            System.out.println("Food addition failed: " + e.getMessage());
        }
    }
}
