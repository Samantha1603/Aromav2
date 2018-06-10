package com.example.android.aroma;

public class Food  {

    private String Name,Image,MenuID, Servings, Time,Ingregients,Directions,Id;

    public Food() {
    }

    public Food(String name, String image, String menuID, String servings, String time, String ingregients, String directions,String id) {
        Name = name;
        Image = image;
        MenuID = menuID;
        Servings = servings;
        Time = time;
        Ingregients = ingregients;
        Directions = directions;
        Id = id;
    }

    public Food(String name, String image,String id) {
        Name = name;
        Image = image;
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getMenuID() {
        return MenuID;
    }

    public void setMenuID(String menuID) {
        MenuID = menuID;
    }

    public String getServings() {
        return Servings;
    }

    public void setServings(String servings) {
        Servings = servings;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    public String getIngredients() {
        return Ingregients;
    }

    public void setIngredients(String ingredients) {
        Ingregients = ingredients;
    }

    public String getDirections() {
        return Directions;
    }

    public void setDirections(String directions) {
        Directions = directions;
    }

    public String getId() {
        return Id;
    }
}
