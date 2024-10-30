package src.view;

import src.controller.OrganizerBookings;
import src.controller.OrganizerDisplay;
import src.model.User;
import java.util.Scanner;

public class organizer {
    Scanner s = new Scanner(System.in);
    public void organizer(User u){
        System.out.println("\nWelcome Organizer !! " + u.getName());
        while(true){
            System.out.println("Enter your choice : \n");
            System.out.println("0. Logout");
            System.out.println("1. View profile");
            System.out.println("2. Add venue");
            System.out.println("3. Add event");
            System.out.println("4. Add food");
            System.out.println("5. View Bookings\n");
            int choice = s.nextInt();
            s.nextLine();
            Venueinfo vi = new Venueinfo();
            switch (choice) {
                case 0:
                    return;
                case 1:
                    OrganizerDisplay od = new OrganizerDisplay();
                    od.viewProfile(u.getId());
                    break;
                case 2:
                    vi.venue(u);
                    break;
                case 3:
                    vi.event(u);
                    break;
                case 4:
                    vi.food(u);
                    break;
                case 5:
                    System.out.println("1. View all bookings");
                    System.out.println("2. View upcoming bookings");
                    int choice1 =s.nextInt();
                    OrganizerBookings ob = new OrganizerBookings();
                    switch(choice1){
                        case 1:
                           ob.ViewBookings(u);
                            break;
                        case 2:
                            ob.ViewUpcomingBookings(u);
                            break;
                    }
            }
        }
    }
}
