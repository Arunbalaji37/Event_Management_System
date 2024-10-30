package src.model;

public class User {
    private int id;
    private String role;
    private String name;
    private String email;
    private String mobileNumber;

    public User(String name, String email, String mobileNumber, int id, String role) {
        this.name = name;
        this.email = email;
        this.mobileNumber = mobileNumber;
        this.id = id;
        this.role = role;
    }
    public int getId() {
        return id;
    }
    public String getRole() {
        return role;
    }
    public String getName() {
        return name;
    }
    public String getEmail() {
        return email;
    }
    public String getPhone() {
        return mobileNumber;
    }
}
