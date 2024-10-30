package src.view;

import src.controller.Queries;
import src.controller.Venue_details;
import src.model.Event;
import src.model.ObjectCreation;
import src.model.User;
import src.model.Venue;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Venueinfo {
    Scanner s = new Scanner(System.in);
    Connection conn = ObjectCreation.getInstanceofDatabaseConnection();
    public void venue(User u) {
        System.out.println("Enter venue name: ");
        String venue_name = s.nextLine();
        System.out.println("Enter venue place: ");
        String venue_place = s.nextLine();
        String venue_contact;
        while(true){
            System.out.println("Enter phone number :");
            venue_contact=s.next();
            if (venue_contact != null && venue_contact.length() > 0 && (venue_contact.charAt(0) == '9' || venue_contact.charAt(0) == '8' || venue_contact.charAt(0) == '7' || venue_contact.charAt(0) == '6') && venue_contact.length()==10) {
                break;
            }else{
                System.out.println("Invalid phone number");
            }
        }
        String venue_email;
        s.nextLine();
        while(true){
            System.out.println("Enter venue email: ");
            venue_email = s.nextLine();
            String EMAIL_REGEX = "^[a-z0-9._%+-]+@(gmail|yahoo|outlook)\\.com$";
            if(venue_email.matches(EMAIL_REGEX)){
                break;
            }else{
                System.out.println("Invalid email please enter email again ");
            }
        }
        System.out.println("Enter venue capacity: ");
        int venue_capacity = s.nextInt();
        s.nextLine();
        System.out.println("Venue 'A/c' or 'Non_A/c': ");
        String venue_ac = s.nextLine();
        venue_ac = venue_ac.equalsIgnoreCase("A/c") ? "A/c" : "Non_A/c";
        Venue v = new Venue(0,venue_name, venue_place, venue_contact, venue_email, venue_capacity, venue_ac);
        Venue_details vd = new Venue_details();
        vd.addVenue(v, u.getId());
    }

    public void event(User u) {
          while(true) {
                    System.out.println("Enter venue email: ");
                    String venueEmail = s.nextLine();
                     String query = Queries.SelectEventForAddVenue;
                    try (PreparedStatement pstmt = conn.prepareStatement(query)) {
                         pstmt.setString(1, venueEmail);
                         ResultSet rs = pstmt.executeQuery();
                         if (rs.next()) {
                              System.out.println("Enter event name: ");
                              String event_name = s.nextLine();
                              System.out.println("Enter event cost: ");
                              double event_cost = s.nextDouble();
                              Event ev = new Event(event_name, event_cost);
                              Venue_details vd = new Venue_details();
                              vd.addEvent(ev, venueEmail, u.getId());
                              break;
                         } else {
                              System.out.println("Venue email not found. Please enter a valid email.");
                         }
                    }catch(SQLException e){
                         System.out.println("Event add failed : "+e);
                    }
               }
          }
     public void food(User u) {
        while(true) {
            System.out.println("Enter venue email: ");
            String venue_email = s.nextLine();
            String query = Queries.SelectFoodForAddVenue;
            try (PreparedStatement pstmt = conn.prepareStatement(query)) {
                pstmt.setString(1, venue_email);
                ResultSet rs = pstmt.executeQuery();
                if (rs.next()) {
                    System.out.println("Enter food: ");
                    String foodSession = s.nextLine();
                    System.out.println("Enter cost of food: ");
                    double foodCost = s.nextDouble();
                    Venue_details vd = new Venue_details();
                    vd.addFood(foodSession, foodCost, venue_email,u.getId());
                    break;
                } else {
                    System.out.println("Venue email not found. Please enter a valid email.");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    }
