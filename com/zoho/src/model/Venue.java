package src.model;

public class Venue {
    private int id;
    private String venue_name;
    private String venue_place;
    private String venue_contact;
    private String venue_email;
    private int venue_capacity;
    private String venue_ac;

    public Venue(int id, String venue_name, String venue_place, String venue_contact, String venue_email, int venue_capacity, String venue_ac) {
        this.id = id;
        this.venue_name = venue_name;
        this.venue_place = venue_place;
        this.venue_contact = venue_contact;
        this.venue_email = venue_email;
        this.venue_capacity = venue_capacity;
        this.venue_ac = venue_ac;
    }

    public int getId() {
        return id;
    }

    public String getVenue_name() {
        return venue_name;
    }

    public String getVenue_place() {
        return venue_place;
    }

    public String getVenue_contact() {
        return venue_contact;
    }

    public String getVenue_email() {
        return venue_email;
    }

    public int getVenue_capacity() {
        return venue_capacity;
    }

    public String getVenue_ac() {
        return venue_ac;
    }
}

