package com.example.vnedutlucsentpamenuapp;

public class FoodItem {
    private int id;
    private String dishName;
    private int cost;
    private String description;
    private String image;

    public FoodItem() {
    }

    public FoodItem(int id, String dishName, int cost, String description, String image) {
        this.id = id;
        this.dishName = dishName;
        this.cost = cost;
        this.description = description;
        this.image = image;
    }

    // Getter & Setter

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDishName() {
        return dishName;
    }

    public void setDishName(String dishName) {
        this.dishName = dishName;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

}
