package src.model;

public class Event {
    private String eventName;
    private double eventCost;
   // private List<model.FoodItem> foodItems;
    private int id;

    public Event(int id, String eventName) {
        this.id = id;
        this.eventName = eventName;
    }

    public Event(int id,int venueid,String eventName,double eventCost){
        this.id=id;
        this.eventName = eventName;
        this.eventCost = eventCost;
    }

    public Event(String eventName, double eventCost) {
        this.eventName = eventName;
        this.eventCost = eventCost;
       // this.foodItems = new ArrayList<>();
       // this.equipmentList = new ArrayList<>();
    }

    public String getEventName() {
        return eventName;
    }
    public double getEventCost() {
        return eventCost;
    }

    public int getId() {
        return id;
    }
}
