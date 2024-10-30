package src.main;

import src.model.User;
import src.view.Authorization;
import src.view.Home;
import src.view.User_detail;
import java.util.Scanner;
import src.model.ObjectCreation;

class Main{
    Scanner s = new Scanner(System.in);
    public static void main(String[] args) {
        Home h = new Home();
        User_detail ud = new User_detail();
        while (true) {
            int choice = h.useraccount();
            if (choice == 0) {
               System.out.println("Thank you! Come again");
              ObjectCreation.closeDatabaseConnection();
                System.exit(0);
            } else if (choice == 1) {
                ud.signUp();
            } else if (choice == 2) {
                User u=ud.login();
                Authorization aut= new Authorization();
                aut.authorize(u);
            }
        }
   
    }
}
