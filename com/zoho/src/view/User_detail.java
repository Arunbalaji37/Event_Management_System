package src.view;

import src.controller.SignIn;
import src.controller.SignUp;
import src.model.User;
import java.io.Console;
import java.util.Scanner;
public class User_detail {
       Scanner s = new Scanner(System.in);
        Console console = System.console();
        public void signUp() {
            System.out.println("Enter your role : (organizer,customer)");
            String role=s.nextLine().toLowerCase();
            System.out.println("Enter name:");
            String n=s.next();
            String e;
            while(true){
                System.out.println("Enter email:");
                e=s.next();
                String EMAIL_REGEX = "^[a-zA-Z0-9._%+-]+@(gmail|yahoo|outlook)\\.com$";
                if(e.matches(EMAIL_REGEX)){
                    break;
                }else{
                    System.out.println("Invalid email ");
                }
            }
            String p;
            while(true){
                System.out.println("Enter phone number :");
                p=s.next();
                if (p != null && p.length() > 0 && (p.charAt(0) == '9' || p.charAt(0) == '8' || p.charAt(0) == '7' || p.charAt(0) == '6') && p.length()==10) {
                    break;
                }else{
                    System.out.println("Invalid phone number");
                }
            }
            System.out.println("Enter username:");
            String un=s.next();
            char[] passwordArray = console.readPassword("Enter your password: ");
            String password = new String(passwordArray);
            User user = new User(n,e,p,0,role);
            SignUp si = new SignUp(user,un,password);
        }
        public User login() {
            System.out.print("Enter Username: ");
            String uname = s.nextLine();
            SignIn sin = new SignIn();
            return sin.userSignIn(uname);
        }
}
