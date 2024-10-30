package src.controller;

import src.model.Event;
import src.model.FoodItem;
import src.model.ObjectCreation;
import src.model.Venue;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Booking {
    private Connection conn = ObjectCreation.getInstanceofDatabaseConnection();
    private Scanner scanner = new Scanner(System.in);

    public int bookPartyHall(int userId) {
        try {
            System.out.println("Available Venues:");
            List<Venue> venues = fetchAvailableVenues();
            for (int i = 0; i < venues.size(); i++) {
                System.out.println((i + 1) + ". " + venues.get(i).getVenue_name() + " - " + venues.get(i).getVenue_place());
            }
            System.out.println("Select a venue by number:");
            int venueSelection = scanner.nextInt();
            if (venueSelection < 1 || venueSelection > venues.size()) {
                System.out.println("Invalid selection. Please try again.");
                return -1;
            }
            int venueId = venues.get(venueSelection - 1).getId();
            scanner.nextLine();
            Date eventDate = getEventDate();
            Time eventTime = getEventTime();
            Timestamp eventTimestamp = Timestamp.valueOf(eventDate + " " + new SimpleDateFormat("HH:mm").format(eventTime) + ":00");

            if (eventTimestamp.before(new Timestamp(System.currentTimeMillis()))) {
                System.out.println("Error: You cannot book for a past date.");
                return -1;
            }

            if (!isVenueAvailable(venueId, eventDate)) {
                System.out.println("Error: The venue is already booked for the selected date.");
                return -1;
            }

            Event selectedEvent = selectEvent(venueId);
            FoodItem selectedFoodItem = selectFoodItem(venueId);
            
            System.out.println("Enter the number of guests:");
            int numberOfAttendees = scanner.nextInt();

            double totalFoodCost = selectedFoodItem.getFoodCost() * numberOfAttendees;
            double totalAmount = calculateTotalAmount(selectedEvent.getEventCost(), totalFoodCost);
            
            int bookingId = createBooking(userId, venueId, selectedEvent.getId(), eventDate, eventTime, selectedEvent.getEventCost(), totalFoodCost, totalAmount);

            if (bookingId != -1) {
                processPayment(bookingId, totalAmount);
            }
            return bookingId;
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    private boolean isVenueAvailable(int venueId, Date eventDate) throws SQLException {
        String query = Queries.FetchDateForBooking;
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, venueId);
            pstmt.setDate(2, eventDate);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) == 0; 
            }
        }
        return false;
    }

    private void processPayment(int bookingId, double totalAmount) {
        System.out.println("Total Amount for Booking ID " + bookingId + ": " + totalAmount);
        System.out.println("Press number: (1 = Full payment or 2 = Advance payment)");
        int pay = scanner.nextInt();
        System.out.println("Enter the amount:");
        double amountPaid = scanner.nextDouble();
        System.out.println("Enter the payment method (e.g., Cash, Credit Card, UPI):");
        scanner.nextLine(); 
        String paymentMethod = scanner.nextLine();

        Payment payment = new Payment(); 

        if (pay == 1) { 
            if (amountPaid == totalAmount) {
                payment.handlePayment(bookingId, amountPaid, paymentMethod, true); 
            } else {
                System.out.println("Amount is less than the total amount! Full payment should be the total amount.");
            }
        } else if (pay == 2) { 
            if (amountPaid < totalAmount) {
                payment.handlePayment(bookingId, amountPaid, paymentMethod, false); 
            } else {
                System.out.println("Amount exceeds the total amount! Advance payment should be less than the total amount.");
            }
        } else {
            System.out.println("Invalid selection. Please choose either 1 or 2.");
        }
    }

    private Date getEventDate() {
        while (true) {
            System.out.println("Enter the date for your event (YYYY-MM-DD):");
            String eventDateString = scanner.nextLine();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            try {
                return new Date(dateFormat.parse(eventDateString).getTime());
            } catch (ParseException e) {
                System.out.println("Invalid date format. Please try again.");
            }
        }
    }

    private Time getEventTime() {
        while (true) {
            System.out.println("Enter the time for your event (HH:MM AM/PM):");
            String eventTimeString = scanner.nextLine();
            SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm a");
            try {
                java.util.Date parsedTime = timeFormat.parse(eventTimeString);
                return new Time(parsedTime.getTime());
            } catch (ParseException e) {
                System.out.println("Invalid time format. Please try again.");
            }
        }
    }

    private Event selectEvent(int venueId) throws SQLException {
        System.out.println("Available events:");
        List<Event> eventList = fetchEvent(venueId);
        for (int i = 0; i < eventList.size(); i++) {
            System.out.println((i + 1) + ". " + eventList.get(i).getEventName() + " - " + eventList.get(i).getEventCost());
        }

        System.out.println("Select an event by number:");
        int eventSelection = scanner.nextInt();
        if (eventSelection < 1 || eventSelection > eventList.size()) {
            System.out.println("Invalid selection. Please try again.");
            return null;
        }
        return eventList.get(eventSelection - 1);
    }

    private FoodItem selectFoodItem(int venueId) throws SQLException {
        System.out.println("Available Food Items:");
        List<FoodItem> foodItems = fetchFoodItems(venueId);
        for (int i = 0; i < foodItems.size(); i++) {
            FoodItem food = foodItems.get(i);
            System.out.println((i + 1) + ". " + food.getFoodName() + " - " + food.getFoodCost() + " per plate");
        }

        System.out.println("Select food items by number:");
        int foodChoice = scanner.nextInt();
        return foodItems.get(foodChoice - 1);
    }

    private double calculateTotalAmount(double eventCost, double foodCost) {
        double totalAmount = eventCost + foodCost;
        System.out.println("Total amount for the booking:");
        System.out.println("Event cost: " + eventCost);
        System.out.println("Food cost: " + foodCost);
        System.out.println("Total cost: " + totalAmount);
        return totalAmount;
    }

    private int createBooking(int userId, int venueId, int eventId, Date eventDate, Time eventTime, double eventCost, double foodCost, double totalAmount) throws SQLException {
        String insertBookingSQL = Queries.InsertBooking;
        int bookingId = -1;

        try (PreparedStatement pstmt = conn.prepareStatement(insertBookingSQL)) {
            pstmt.setInt(1, userId);
            pstmt.setInt(2, venueId);
            pstmt.setInt(3, eventId);
            pstmt.setDate(4, eventDate);
            pstmt.setTime(5, eventTime);
            pstmt.setDouble(6, eventCost);
            pstmt.setDouble(7, foodCost);
            pstmt.setDouble(8, totalAmount);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                bookingId = rs.getInt("id");
                System.out.println("Booking successful! Booking ID: " + bookingId);
            } else {
                System.out.println("Booking failed.");
            }
        }
        createPaymentDetail(bookingId, totalAmount);
        return bookingId;
    }

    private void createPaymentDetail(int bookingId, double totalAmount) throws SQLException {
        String paymentDetailSQL = Queries.InsertPaymentDetails;
        try (PreparedStatement pstmt = conn.prepareStatement(paymentDetailSQL)) {
            pstmt.setInt(1, bookingId);
            pstmt.setDouble(2, 0);
            pstmt.setDouble(3, totalAmount);
            pstmt.executeUpdate();
        }
        System.out.println("Payment details added for the booking.");
    }

    private List<Venue> fetchAvailableVenues() throws SQLException {
        List<Venue> venues = new ArrayList<>();
        String query = Queries.FetchVenue;
        try (PreparedStatement pstmt = conn.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                Venue venue = new Venue(rs.getInt("id"), rs.getString("venue_name"), rs.getString("venue_place"), null, null, 0, null);
                venues.add(venue);
            }
        }
        return venues;
    }

    private List<Event> fetchEvent(int venueId) throws SQLException {
        List<Event> eventList = new ArrayList<>();
        String query = Queries.FetchEvent;
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, venueId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Event event = new Event(rs.getInt("id"), venueId, rs.getString("event_name"), rs.getDouble("event_cost"));
                    eventList.add(event);
                }
            }
        }
        return eventList;
    }

    private List<FoodItem> fetchFoodItems(int venueId) throws SQLException {
        List<FoodItem> foodItems = new ArrayList<>();
        String query = Queries.FetchFood;
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, venueId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    FoodItem food = new FoodItem(rs.getInt("id"), venueId, rs.getString("food_name"), rs.getDouble("food_cost"));
                    foodItems.add(food);
                }
            }
        }
        return foodItems;
    }
}

