package src.view;

import src.controller.AdminDisplay;
import src.model.User;
import java.util.Scanner;
public class Admin {
    Scanner s = new Scanner(System.in);
    public void admin(User u){
        System.out.println("\nWelcome Admin : "+u.getName());
        while(true) {
            System.out.println("1. View Organizer");
            System.out.println("2. View customer");
            System.out.println("3. View bookings");
            System.out.println("4. View venues");
            System.out.println("Enter your choice : ");
            int choice=s.nextInt();
            AdminDisplay ad = new AdminDisplay();
            switch(choice){
                case 0:
                    return;
                case 1:
                    ad.ViewOrganizers();
                    break;
                case 2:
                    ad.ViewCustomers();
                    break;
                case 3:
                    ad.ViewBookingDetails();
                    break;
                case 4:
                    ad.ViewVenue();
                    break;
            }
        }
    }
}
