package src.view;

import src.model.User;

public class Authorization {
    public void authorize(User u) {
        Home h = new Home();
        User_detail ud = new User_detail();
        try{
        switch (u.getRole()) {
            case "admin":
                Admin a = new Admin();
                a.admin(u);
                break;
            case "organizer":
                organizer o = new organizer();
                o.organizer(u);
                break;
            case "customer":
                customer c = new customer();
                c.customer(u);
                break;
            case "error":
                System.out.println("Something went wrong");
                break;
        }
        }catch(NullPointerException e){
               System.out.println("user not found");       
        }
    }
}
