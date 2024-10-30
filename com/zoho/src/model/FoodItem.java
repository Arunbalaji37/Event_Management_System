package src.model;

public class FoodItem {
    private int id;
    private int venue_id;
    private String food_name;
    private double food_cost;

    public FoodItem(int id, int venue_id, String food_name, double food_cost) {
        this.id = id;
        this.venue_id = venue_id;
        this.food_name = food_name;
        this.food_cost = food_cost;
    }

    public int getId() {
        return id;
    }

    public String getFoodName() {
        return food_name;
    }

    public double getFoodCost() {
        return food_cost;
    }
}
