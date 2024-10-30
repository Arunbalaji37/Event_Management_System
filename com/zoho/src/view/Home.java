package src.view;

import java.util.Scanner;
public class Home {
        Scanner scanner= new Scanner(System.in);
        public int useraccount() {
               System.out.println("0. Exit");
                System.out.println("1. Sign up");
                System.out.println("2. Sign in");
                System.out.println("Enter your choice : \n");
                int choice = scanner.nextInt();
                return choice;
        }
}
